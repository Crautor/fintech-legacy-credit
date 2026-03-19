package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;

public interface ServicoAnaliseRisco {
    boolean avaliarCredito(SolicitacaoCreditoRecord solicitacao);
}