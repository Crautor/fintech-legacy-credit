package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio;

import java.util.List;

/**
 * BLACK-BOX FRAMEWORK — Motor (Engine) de exportação de relatórios.
 *
 * <p>Funciona como <b>caixa-preta</b>: recebe as estratégias de formatação
 * e persistência via construtor (Injeção de Dependência) e controla todo o fluxo.</p>
 *
 * <p><b>Inversão de Controle:</b> o engine decide quando chamar o formatador
 * e quando chamar a persistência. O cliente só implementa as interfaces.</p>
 *
 * <p><b>Vantagens sobre White-box:</b></p>
 * <ul>
 *   <li>Formatação e persistência são estratégias independentes</li>
 *   <li>Pode combinar FormatadorPdf + PersistenciaCloud sem criar nova subclasse</li>
 *   <li>Cada componente é testável isoladamente</li>
 *   <li>Sem hierarquia de herança</li>
 * </ul>
 */
public class ExportadorRelatorioEngine {

    private final FormatadorRelatorio formatador;
    private final PersistenciaRelatorio persistencia;

    /**
     * Construtor com Injeção de Dependência.
     * Recebe as estratégias externamente (composição, não herança).
     *
     * @param formatador   estratégia de formatação do relatório
     * @param persistencia estratégia de persistência do relatório
     */
    public ExportadorRelatorioEngine(FormatadorRelatorio formatador, PersistenciaRelatorio persistencia) {
        this.formatador = formatador;
        this.persistencia = persistencia;
    }

    /**
     * Exporta o relatório usando as estratégias injetadas.
     *
     * <p><b>IoC em ação:</b> o engine controla o fluxo e chama
     * o código do cliente nos momentos certos.</p>
     *
     * @param dados         lista de dados a serem exportados
     * @param nomeArquivo   nome do arquivo de saída
     * @return o relatório completo formatado
     */
    public String exportar(List<String> dados, String nomeArquivo) {
        System.out.println("\n[Black-box Relatório Engine] Iniciando exportação...");

        if (dados == null || dados.isEmpty()) {
            System.out.println("[Black-box Relatório Engine] Sem dados para exportar.");
            return "";
        }

        // IoC: o engine chama o formatador do cliente
        String cabecalho = formatador.formatarCabecalho();
        System.out.println("[Black-box Relatório Engine] ✔ Cabeçalho formatado");

        String corpo = formatador.formatarDados(dados);
        System.out.println("[Black-box Relatório Engine] ✔ Dados formatados (" + dados.size() + " registros)");

        String rodape = formatador.formatarRodape();
        System.out.println("[Black-box Relatório Engine] ✔ Rodapé formatado");

        String relatorio = cabecalho + corpo + rodape;

        // IoC: o engine chama a persistência do cliente
        persistencia.salvar(relatorio, nomeArquivo);
        System.out.println("[Black-box Relatório Engine] ✅ Exportação concluída!");

        return relatorio;
    }
}
