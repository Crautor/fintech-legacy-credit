package br.com.nogueiranogueira.aularefatoracao.service.relatorio;

import java.util.List;

public abstract class GeradorRelatorioTemplate {

    // O método 'final' impede que subclasses alterem a ordem do algoritmo principal
    public final void gerarRelatorio(String dataReferencia) { // [cite: 109, 132]
        System.out.println("--- Iniciando Geração de Relatório para: " + dataReferencia + "---"); // [cite: 110]

        List<String> dados = extrairDadosDoBanco(); // [cite: 111]

        if (dados.isEmpty()) { // [cite: 112]
            System.out.println("Sem dados para exportar."); // [cite: 113]
            return;
        }

        String cabecalho = formatarCabecalho(); // [cite: 117]
        String corpo = formatarCorpo(dados); // [cite: 118]
        salvarArquivo(cabecalho + corpo); // [cite: 119]
    }

    private List<String> extrairDadosDoBanco() { // [cite: 120]
        System.out.println("[Banco] Executando SELECT * FROM analises_credito..."); // [cite: 122]
        return List.of("123.456.789-00 - R$ 5000 - APROVADO", "987.654.321-11 - R$ 1000 - APROVADO"); // [cite: 123]
    }

    private void salvarArquivo(String conteudo) { // [cite: 124]
        System.out.println("[Disco] Salvando ficheiro na rede...\n" + conteudo); // [cite: 126]
    }

    // Passos variáveis que as subclasses devem implementar
    protected abstract String formatarCabecalho(); // [cite: 127, 134]
    protected abstract String formatarCorpo(List<String> dados); // [cite: 128, 135]
}