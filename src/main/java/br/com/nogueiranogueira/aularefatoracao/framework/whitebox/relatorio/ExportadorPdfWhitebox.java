package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CÓDIGO-CLIENTE — Exportador PDF White-box.
 *
 * <p>ESTENDE o framework ({@code extends ExportadorRelatorioTemplate})
 * e sobrescreve os hook methods para gerar um relatório no formato PDF.</p>
 *
 * <p><b>Nota:</b> como é uma simulação, o "PDF" é representado como texto
 * formatado com marcações visuais que simulam a estrutura de um documento PDF.</p>
 */
public class ExportadorPdfWhitebox extends ExportadorRelatorioTemplate {

    @Override
    protected String gerarCabecalho() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return "╔══════════════════════════════════════════════╗\n"
             + "║     RELATÓRIO DE ANÁLISE DE CRÉDITO (PDF)    ║\n"
             + "║     Fintech Legacy Credit                    ║\n"
             + "║     Gerado em: " + dataHora + "         ║\n"
             + "╠══════════════════════════════════════════════╣\n"
             + "║  DOCUMENTO       │  VALOR     │  STATUS      ║\n"
             + "╠══════════════════════════════════════════════╣\n";
    }

    @Override
    protected String formatarDados(List<String> dados) {
        StringBuilder corpo = new StringBuilder();
        for (String dado : dados) {
            corpo.append("║  ").append(String.format("%-44s", dado.replace(" - ", "  │  "))).append("║\n");
        }
        return corpo.toString();
    }

    @Override
    protected String gerarRodape() {
        return "╠══════════════════════════════════════════════╣\n"
             + "║  Documento gerado automaticamente            ║\n"
             + "║  © 2026 Fintech Legacy Credit                ║\n"
             + "╚══════════════════════════════════════════════╝\n";
    }

    @Override
    protected void salvarRelatorio(String conteudo) {
        // Simulação de salvamento em disco como PDF
        System.out.println("[PDF White-box] Salvando relatório em /reports/credito_" +
                System.currentTimeMillis() + ".pdf");
    }
}
