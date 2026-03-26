package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;

public class AnaliseStrategySsn implements AnaliseStrategy {
    @Override
    public boolean analisar(SolicitacaoCreditoRecord solicitacao) {
        return true;
    }
}
