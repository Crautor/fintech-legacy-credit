package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

import java.math.BigDecimal;

/**
 * CÓDIGO-CLIENTE — Regra Black-box: Validação de Valor Máximo.
 *
 * <p>IMPLEMENTA a interface {@code RegraValidacao} (implements, não extends).</p>
 * <p>Valida se o valor da transação não excede o limite configurado.</p>
 *
 * <p><b>Vantagem Black-box:</b> o limite é configurável via construtor,
 * permitindo reutilização da mesma regra com diferentes limites.</p>
 */
public class RegraValorMaximo implements RegraValidacao {

    private final BigDecimal limiteMaximo;

    /**
     * Cria a regra com um limite máximo configurável.
     *
     * @param limiteMaximo valor máximo permitido para a transação
     */
    public RegraValorMaximo(BigDecimal limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }

    @Override
    public ResultadoValidacao validar(Transacao transacao) {
        boolean valido = transacao.valor().compareTo(limiteMaximo) <= 0;

        return valido
                ? ResultadoValidacao.sucesso()
                : ResultadoValidacao.falha("Valor R$ " + transacao.valor()
                    + " excede o limite máximo de R$ " + limiteMaximo);
    }

    @Override
    public String descricao() {
        return "Validação de Valor Máximo (limite: R$ " + limiteMaximo + ")";
    }
}
