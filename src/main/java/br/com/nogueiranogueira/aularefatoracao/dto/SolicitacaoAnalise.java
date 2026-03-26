package br.com.nogueiranogueira.aularefatoracao.dto;

public record SolicitacaoAnalise(
        String cliente,
        String documento, // <-- Campo novo adicionado aqui
        double valor,
        int score,
        boolean negativado,
        TipoConta tipoConta,
        Pais pais
) {
}