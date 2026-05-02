package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

import java.math.BigDecimal;

/**
 * CÓDIGO-CLIENTE — Validação White-box para Pessoa Jurídica.
 *
 * <p>ESTENDE o framework ({@code extends TransacaoValidadorTemplate})
 * e sobrescreve os hook methods com regras de PJ.</p>
 *
 * <p><b>Regras de negócio PJ:</b></p>
 * <ul>
 *   <li>Documento deve ter 14 dígitos (CNPJ)</li>
 *   <li>Valor máximo: R$ 500.000,00</li>
 *   <li>Score mínimo: 500</li>
 * </ul>
 */
public class ValidadorTransacaoPJ extends TransacaoValidadorTemplate {

    private static final BigDecimal LIMITE_PJ = new BigDecimal("500000.00");
    private static final int SCORE_MINIMO_PJ = 500;

    @Override
    protected boolean validarDocumento(Transacao transacao) {
        // CNPJ deve conter exatamente 14 dígitos numéricos
        String doc = transacao.documento().replaceAll("[^0-9]", "");
        return doc.length() == 14;
    }

    @Override
    protected boolean validarValor(Transacao transacao) {
        // PJ: limite de R$ 500.000
        return transacao.valor().compareTo(LIMITE_PJ) <= 0;
    }

    @Override
    protected boolean validarRegrasEspecificas(Transacao transacao) {
        // PJ: score mínimo de 500
        return transacao.score() >= SCORE_MINIMO_PJ;
    }
}
