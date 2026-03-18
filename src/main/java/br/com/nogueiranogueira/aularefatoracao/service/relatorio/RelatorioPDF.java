package br.com.nogueiranogueira.aularefatoracao.service.relatorio;

import java.util.List;

public class RelatorioPDF extends GeradorRelatorioTemplate {

    @Override
    protected String formatarCabecalho() {
        return "=== RELATÓRIO DE ANÁLISE DE CRÉDITO (PDF) ===\n\n";
    }

    @Override
    protected String formatarCorpo(List<String> dados) {
        StringBuilder corpo = new StringBuilder();
        // Formata os dados de uma forma mais visual para leitura em PDF
        for (String dado : dados) {
            corpo.append("-> Registo: ").append(dado).append("\n");
        }
        return corpo.toString();
    }
}