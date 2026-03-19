package br.com.nogueiranogueira.aularefatoracao.service.relatorio;

import java.util.List;

public class RelatorioPDF extends GeradorRelatorioTemplate {

    @Override
    protected String formatarCabecalho() {
        return "=== RELATÓRIO DE CRÉDITO (PDF) ===\nDOCUMENTO | VALOR | STATUS\n";
    }

    @Override
    protected String formatarCorpo(List<String> dados) {
        StringBuilder corpo = new StringBuilder();
        for (String dado : dados) {
            corpo.append(dado.replace(" - ", " | ")).append("\n");
        }
        return corpo.toString();
    }
}