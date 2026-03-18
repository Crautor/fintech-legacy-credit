package br.com.nogueiranogueira.aularefatoracao.dto;

import java.math.BigDecimal;

public record SolicitacaoCreditoRecord(
        String cliente,
        String documento, // Nova propriedade adicionada
        BigDecimal valor,
        int score,
        boolean negativado,
        TipoConta tipo
) {
}