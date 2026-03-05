package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;

public interface SolicitacaoStrategy {
    boolean Analisar(SolicitacaoAnalise solicitacao);
    boolean Elegivel(SolicitacaoAnalise solicitacao);
}
