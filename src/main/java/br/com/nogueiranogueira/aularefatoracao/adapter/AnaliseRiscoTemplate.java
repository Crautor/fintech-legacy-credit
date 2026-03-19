package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;

public abstract class AnaliseRiscoTemplate implements ServicoAnaliseRisco {

    @Override
    public final boolean avaliarCredito(SolicitacaoCreditoRecord solicitacao) {
        System.out.println("[Template] Iniciando integração externa para o documento: " + solicitacao.documento());
        
        String requestPayload = montarRequisicao(solicitacao);
        String responsePayload = enviarRequisicao(requestPayload);
        
        return processarResposta(responsePayload);
    }

    protected abstract String montarRequisicao(SolicitacaoCreditoRecord solicitacao);
    protected abstract String enviarRequisicao(String payload);
    protected abstract boolean processarResposta(String resposta);
}