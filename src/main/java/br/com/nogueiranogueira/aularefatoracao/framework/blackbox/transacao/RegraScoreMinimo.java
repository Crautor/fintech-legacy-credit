package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

/**
 * CÓDIGO-CLIENTE — Regra Black-box: Validação de Score Mínimo.
 *
 * <p>IMPLEMENTA a interface {@code RegraValidacao} (implements, não extends).</p>
 * <p>Valida se o score de crédito atende ao mínimo configurado.</p>
 *
 * <p><b>Vantagem Black-box:</b> o score mínimo é configurável,
 * podendo ser diferente para PF e PJ sem criar subclasses.</p>
 */
public class RegraScoreMinimo implements RegraValidacao {

    private final int scoreMinimo;

    /**
     * Cria a regra com um score mínimo configurável.
     *
     * @param scoreMinimo score mínimo exigido
     */
    public RegraScoreMinimo(int scoreMinimo) {
        this.scoreMinimo = scoreMinimo;
    }

    @Override
    public ResultadoValidacao validar(Transacao transacao) {
        boolean valido = transacao.score() >= scoreMinimo;

        return valido
                ? ResultadoValidacao.sucesso()
                : ResultadoValidacao.falha("Score " + transacao.score()
                    + " abaixo do mínimo exigido (" + scoreMinimo + ")");
    }

    @Override
    public String descricao() {
        return "Validação de Score Mínimo (mínimo: " + scoreMinimo + ")";
    }
}
