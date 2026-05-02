package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.relatorio;

import java.util.List;

/**
 * WHITE-BOX FRAMEWORK — Exportação de Relatórios via Template Method.
 *
 * <p>Esta classe abstrata define o <b>esqueleto do algoritmo</b> de exportação.
 * O fluxo é controlado pelo framework (IoC — Inversão de Controle).</p>
 *
 * <p>O código-cliente ESTENDE esta classe e sobrescreve os métodos abstratos
 * para definir como formatar e salvar o relatório.</p>
 *
 * <p><b>Cenário:</b> este framework seria "vendido" para outras empresas.
 * O cliente só precisa estender e implementar os métodos abstratos.</p>
 *
 * <p><b>Padrão:</b> Template Method</p>
 * <p><b>Extensão:</b> via herança (extends)</p>
 */
public abstract class ExportadorRelatorioTemplate {

    /**
     * TEMPLATE METHOD — define o algoritmo de exportação.
     * Marcado como {@code final} para garantir a ordem dos passos (IoC).
     *
     * @param dados lista de dados a serem exportados
     * @return o relatório completo formatado
     */
    public final String exportar(List<String> dados) {
        System.out.println("\n[White-box Relatório] Iniciando exportação...");

        if (dados == null || dados.isEmpty()) {
            System.out.println("[White-box Relatório] Sem dados para exportar.");
            return "";
        }

        // Passo 1: Gerar cabeçalho
        String cabecalho = gerarCabecalho();
        System.out.println("[White-box Relatório] ✔ Cabeçalho gerado");

        // Passo 2: Formatar dados
        String corpo = formatarDados(dados);
        System.out.println("[White-box Relatório] ✔ Dados formatados (" + dados.size() + " registros)");

        // Passo 3: Gerar rodapé
        String rodape = gerarRodape();
        System.out.println("[White-box Relatório] ✔ Rodapé gerado");

        // Passo 4: Montar e salvar
        String relatorio = cabecalho + corpo + rodape;
        salvarRelatorio(relatorio);
        System.out.println("[White-box Relatório] ✅ Exportação concluída!");

        return relatorio;
    }

    // ─── Hook Methods — subclasses DEVEM implementar ───────────────

    /** Gera o cabeçalho do relatório no formato desejado. */
    protected abstract String gerarCabecalho();

    /** Formata os dados no formato desejado (PDF, CSV, XML, etc). */
    protected abstract String formatarDados(List<String> dados);

    /** Gera o rodapé do relatório. */
    protected abstract String gerarRodape();

    /** Salva o relatório no destino desejado (disco, rede, cloud, etc). */
    protected abstract void salvarRelatorio(String conteudo);
}
