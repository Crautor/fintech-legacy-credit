package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;
import com.validador.core.domain.Cpf;
import com.validador.core.service.ValidadorDocumentoService;

import java.math.BigDecimal;

/**
 * CÓDIGO-CLIENTE — Validação White-box para Pessoa Física.
 *
 * <p>ESTENDE o framework ({@code extends TransacaoValidadorTemplate})
 * e sobrescreve os hook methods com regras de PF.</p>
 *
 * <p><b>Regras de negócio PF:</b></p>
 * <ul>
 *   <li>Documento validado via {@link ValidadorDocumentoService} do validator-core</li>
 *   <li>Valor máximo: R$ 50.000,00</li>
 *   <li>Score mínimo: 300</li>
 * </ul>
 */
public class ValidadorTransacaoPF extends TransacaoValidadorTemplate {

    private static final BigDecimal LIMITE_PF = new BigDecimal("50000.00");
    private static final int SCORE_MINIMO_PF = 300;

    private final ValidadorDocumentoService validadorService = new ValidadorDocumentoService();

    @Override
    protected boolean validarDocumento(Transacao transacao) {
        try {
            Cpf cpf = new Cpf(transacao.documento());
            return validadorService.validar(cpf).valido();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    protected boolean validarValor(Transacao transacao) {
        // PF: limite de R$ 50.000
        return transacao.valor().compareTo(LIMITE_PF) <= 0;
    }

    @Override
    protected boolean validarRegrasEspecificas(Transacao transacao) {
        // PF: score mínimo de 300
        return transacao.score() >= SCORE_MINIMO_PF;
    }
}
