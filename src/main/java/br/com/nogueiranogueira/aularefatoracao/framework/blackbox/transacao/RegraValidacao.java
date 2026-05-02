package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

/**
 * BLACK-BOX FRAMEWORK — Interface de regra de validação.
 *
 * <p>O código-cliente IMPLEMENTA esta interface ({@code implements RegraValidacao})
 * para fornecer regras de negócio individuais.</p>
 *
 * <p>Cada regra é independente e pode ser combinada livremente via composição,
 * ao contrário do White-box onde tudo fica acoplado na hierarquia de herança.</p>
 *
 * <p><b>Padrão:</b> Strategy</p>
 * <p><b>Extensão:</b> via composição (implements)</p>
 */
public interface RegraValidacao {

    /**
     * Executa a validação da regra sobre a transação.
     *
     * @param transacao a transação a ser validada
     * @return resultado da validação
     */
    ResultadoValidacao validar(Transacao transacao);

    /**
     * Retorna uma descrição legível da regra.
     *
     * @return nome/descrição da regra
     */
    String descricao();
}
