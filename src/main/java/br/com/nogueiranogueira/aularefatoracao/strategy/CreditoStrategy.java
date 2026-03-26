package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;

public interface CreditoStrategy {
    /**
     * Executa a análise de crédito específica da estratégia.
     */
    boolean analisar(SolicitacaoAnalise solicitacao);
    String getPais();
}