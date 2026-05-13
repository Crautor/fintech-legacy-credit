package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;
import com.validador.core.domain.Cnpj;
import com.validador.core.service.ValidadorDocumentoService;

import java.math.BigDecimal;

/**
 * CÓDIGO-CLIENTE — Validação White-box para Pessoa Jurídica.
 *
 * <p>ESTENDE o framework ({@code extends TransacaoValidadorTemplate})
 * e sobrescreve os hook methods com regras de PJ.</p>
 *
 * <p><b>Regras de negócio PJ:</b></p>
 * <ul>
 *   <li>Documento validado via {@link ValidadorDocumentoService} do validator-core</li>
 *   <li>Valor máximo: R$ 500.000,00</li>
 *   <li>Score mínimo: 500</li>
 * </ul>
 */
public class ValidadorTransacaoPJ extends TransacaoValidadorTemplate {

    private static final BigDecimal LIMITE_PJ = new BigDecimal("500000.00");
    private static final int SCORE_MINIMO_PJ = 500;

    private final ValidadorDocumentoService validadorService = new ValidadorDocumentoService();

    @Override
    protected boolean validarDocumento(Transacao transacao) {
        try {
            Cnpj cnpj = new Cnpj(transacao.documento());
            return validadorService.validar(cnpj).valido();
        } catch (IllegalArgumentException e) {
            return false;
        }
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
