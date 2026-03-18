package br.com.nogueiranogueira.aularefatoracao.service.relatorio;

import java.util.List;

public class RelatorioCSV extends GeradorRelatorioTemplate {

    @Override
    protected String formatarCabecalho() {
        return "DOCUMENTO,VALOR,STATUS\n";
    }

    @Override
    protected String formatarCorpo(List<String> dados) {
        StringBuilder corpo = new StringBuilder();
        // Converte o formato do banco (que vem com " - ") para vírgulas, padrão do CSV
        for (String dado : dados) {
            corpo.append(dado.replace(" - ", ",")).append("\n");
        }
        return corpo.toString();
    }
}