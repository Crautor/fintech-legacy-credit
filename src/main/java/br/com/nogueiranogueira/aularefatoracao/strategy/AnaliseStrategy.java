package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;

public interface AnaliseStrategy {

    boolean analisar(SolicitacaoCreditoRecord solicitacao);

    default boolean elegivel(SolicitacaoCreditoRecord solicitacao) {
        return true;
    }

}
