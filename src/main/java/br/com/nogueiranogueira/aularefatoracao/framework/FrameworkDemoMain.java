package br.com.nogueiranogueira.aularefatoracao.framework;

import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio.ExportadorRelatorioEngine;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio.FormatadorPdf;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio.FormatadorRelatorio;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio.PersistenciaDisco;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio.PersistenciaRelatorio;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao.RegraDocumento;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao.RegraScoreMinimo;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao.RegraValorMaximo;
import br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao.TransacaoValidadorEngine;
import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;
import br.com.nogueiranogueira.aularefatoracao.framework.whitebox.relatorio.ExportadorPdfWhitebox;
import br.com.nogueiranogueira.aularefatoracao.framework.whitebox.relatorio.ExportadorRelatorioTemplate;
import br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao.TransacaoValidadorTemplate;
import br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao.ValidadorTransacaoPF;
import br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao.ValidadorTransacaoPJ;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe de demonstração dos Frameworks White-box vs Black-box.
 *
 * <p>Mostra a <b>Inversão de Controle (IoC)</b> em ação:
 * em ambos os casos, é o framework que chama o código do cliente,
 * não o contrário ("Don't call us, we'll call you").</p>
 *
 * <p>Executa 4 demonstrações:</p>
 * <ol>
 *   <li>Validação de Transação — White-box (Template Method + extends)</li>
 *   <li>Validação de Transação — Black-box (Strategy + implements)</li>
 *   <li>Exportação de Relatório — White-box (Template Method + extends)</li>
 *   <li>Exportação de Relatório — Black-box (Strategy + implements)</li>
 * </ol>
 */
public class FrameworkDemoMain {

    private static final String SEPARADOR = "============================================================";

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          DEMONSTRAÇÃO: FRAMEWORKS WHITE-BOX vs BLACK-BOX    ║");
        System.out.println("║          Inversão de Controle (IoC) em Ação                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ─── Dados de teste ────────────────────────────────────────────
        Transacao transacaoPF = new Transacao("123.456.789-00", new BigDecimal("25000.00"), 650, "PF");
        Transacao transacaoPJ = new Transacao("12.345.678/0001-90", new BigDecimal("150000.00"), 720, "PJ");
        Transacao transacaoInvalida = new Transacao("123", new BigDecimal("999999.00"), 100, "PF");

        // ═══════════════════════════════════════════════════════════════
        // 1. VALIDAÇÃO — WHITE-BOX (Template Method + extends)
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n" + SEPARADOR);
        System.out.println("  1. VALIDAÇÃO WHITE-BOX (Template Method + extends)");
        System.out.println("     Padrão: Template Method");
        System.out.println("     Extensão: via herança (extends)");
        System.out.println("     IoC: método final validar() controla o fluxo");
        System.out.println(SEPARADOR);

        // O cliente ESTENDE o framework
        TransacaoValidadorTemplate validadorPF = new ValidadorTransacaoPF();
        TransacaoValidadorTemplate validadorPJ = new ValidadorTransacaoPJ();

        // IoC: o framework chama os métodos do cliente
        ResultadoValidacao resultadoPF = validadorPF.validar(transacaoPF);
        System.out.println("Resultado PF: " + resultadoPF);

        ResultadoValidacao resultadoPJ = validadorPJ.validar(transacaoPJ);
        System.out.println("Resultado PJ: " + resultadoPJ);

        ResultadoValidacao resultadoInvalido = validadorPF.validar(transacaoInvalida);
        System.out.println("Resultado inválido: " + resultadoInvalido);

        // ═══════════════════════════════════════════════════════════════
        // 2. VALIDAÇÃO — BLACK-BOX (Strategy + implements)
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n" + SEPARADOR);
        System.out.println("  2. VALIDAÇÃO BLACK-BOX (Strategy + implements)");
        System.out.println("     Padrão: Strategy");
        System.out.println("     Extensão: via composição (implements)");
        System.out.println("     IoC: engine decide quando chamar cada regra");
        System.out.println(SEPARADOR);

        // O cliente IMPLEMENTA interfaces e INJETA no engine (DI)
        TransacaoValidadorEngine enginePF = new TransacaoValidadorEngine(List.of(
                new RegraDocumento(),
                new RegraValorMaximo(new BigDecimal("50000.00")),
                new RegraScoreMinimo(300)
        ));

        TransacaoValidadorEngine enginePJ = new TransacaoValidadorEngine(List.of(
                new RegraDocumento(),
                new RegraValorMaximo(new BigDecimal("500000.00")),
                new RegraScoreMinimo(500)
        ));

        // IoC: o engine chama as regras do cliente
        ResultadoValidacao resultadoBBPF = enginePF.validar(transacaoPF);
        System.out.println("Resultado PF: " + resultadoBBPF);

        ResultadoValidacao resultadoBBPJ = enginePJ.validar(transacaoPJ);
        System.out.println("Resultado PJ: " + resultadoBBPJ);

        ResultadoValidacao resultadoBBInvalido = enginePF.validar(transacaoInvalida);
        System.out.println("Resultado inválido: " + resultadoBBInvalido);

        // ═══════════════════════════════════════════════════════════════
        // 3. RELATÓRIO — WHITE-BOX (Template Method + extends)
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n" + SEPARADOR);
        System.out.println("  3. RELATÓRIO WHITE-BOX (Template Method + extends)");
        System.out.println("     Padrão: Template Method");
        System.out.println("     Extensão: via herança (extends)");
        System.out.println("     Cliente exporta PDF");
        System.out.println(SEPARADOR);

        List<String> dadosRelatorio = List.of(
                "123.456.789-00 - R$ 25.000 - APROVADO",
                "987.654.321-11 - R$ 5.000 - APROVADO",
                "111.222.333-44 - R$ 80.000 - REPROVADO"
        );

        // O cliente ESTENDE o framework
        ExportadorRelatorioTemplate exportadorWB = new ExportadorPdfWhitebox();
        String relatorioPdfWB = exportadorWB.exportar(dadosRelatorio);
        System.out.println("\n--- Conteúdo do Relatório White-box ---");
        System.out.println(relatorioPdfWB);

        // ═══════════════════════════════════════════════════════════════
        // 4. RELATÓRIO — BLACK-BOX (Strategy + implements)
        // ═══════════════════════════════════════════════════════════════
        System.out.println(SEPARADOR);
        System.out.println("  4. RELATÓRIO BLACK-BOX (Strategy + implements)");
        System.out.println("     Padrão: Strategy");
        System.out.println("     Extensão: via composição (implements)");
        System.out.println("     Cliente exporta PDF + salva em disco");
        System.out.println(SEPARADOR);

        // O cliente IMPLEMENTA interfaces e INJETA no engine (DI)
        FormatadorRelatorio formatadorPdf = new FormatadorPdf();
        PersistenciaRelatorio persistenciaDisco = new PersistenciaDisco();
        ExportadorRelatorioEngine engineRelatorio = new ExportadorRelatorioEngine(formatadorPdf, persistenciaDisco);

        String relatorioPdfBB = engineRelatorio.exportar(dadosRelatorio, "credito_analise.pdf");
        System.out.println("\n--- Conteúdo do Relatório Black-box ---");
        System.out.println(relatorioPdfBB);

        // ═══════════════════════════════════════════════════════════════
        // COMPARAÇÃO FINAL
        // ═══════════════════════════════════════════════════════════════
        System.out.println(SEPARADOR);
        System.out.println("  COMPARAÇÃO FINAL");
        System.out.println(SEPARADOR);
        System.out.println();
        System.out.println("┌──────────────────┬──────────────────────┬──────────────────────┐");
        System.out.println("│   Critério       │   WHITE-BOX          │   BLACK-BOX          │");
        System.out.println("├──────────────────┼──────────────────────┼──────────────────────┤");
        System.out.println("│   Extensão       │   Herança (extends)  │   Composição (impl.) │");
        System.out.println("│   Padrão         │   Template Method    │   Strategy           │");
        System.out.println("│   Acoplamento    │   Alto               │   Baixo              │");
        System.out.println("│   Testabilidade  │   Difícil            │   Fácil              │");
        System.out.println("│   Flexibilidade  │   Limitada           │   Alta               │");
        System.out.println("│   IoC            │   Método final       │   Engine/Motor       │");
        System.out.println("│   Spring         │   Legado             │   Moderno            │");
        System.out.println("└──────────────────┴──────────────────────┴──────────────────────┘");
        System.out.println();
        System.out.println("Conclusão: Spring moderno é massivamente Black-box!");
        System.out.println("→ Composição sobre herança");
        System.out.println("→ Software mais testável e menos acoplado");
    }
}
