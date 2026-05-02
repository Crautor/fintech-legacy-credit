package br.com.nogueiranogueira.aularefatoracao.framework.model;

import java.math.BigDecimal;

/**
 * Record que representa uma transação financeira.
 * Modelo compartilhado entre os frameworks White-box e Black-box.
 */
public record Transacao(
        String documento,
        BigDecimal valor,
        int score,
        String tipo // "PF" ou "PJ"
) {
    @Override
    public String toString() {
        return "Transacao{doc='" + documento + "', valor=" + valor + ", score=" + score + ", tipo='" + tipo + "'}";
    }
}
