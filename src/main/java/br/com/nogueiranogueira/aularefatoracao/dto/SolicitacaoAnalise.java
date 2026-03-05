package br.com.nogueiranogueira.aularefatoracao.dto;

public record SolicitacaoAnalise(String cliente, double valor, int score, boolean negativado, TipoConta tipoConta) {
}
