package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;

public interface ServicoAnaliseRiscoExterno {
    boolean avaliarRisco(SolicitacaoCredito solicitacao);
}
