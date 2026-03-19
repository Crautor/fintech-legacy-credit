package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AnaliseRiscoTemplate implements ServicoAnaliseRisco {

    private static final Logger log = LoggerFactory.getLogger(AnaliseRiscoTemplate.class);

    @Override
    public final boolean avaliarCredito(SolicitacaoCreditoRecord solicitacao) {
        log.info("[Template] Iniciando integracao externa para documento {}", solicitacao.documento());

        if (!validarSolicitacao(solicitacao)) {
            log.warn("[Template] Solicitacao invalida para analise externa");
            return false;
        }

        try {
            String requestPayload = montarRequisicao(solicitacao);
            String responsePayload = enviarRequisicao(requestPayload);
            boolean aprovado = processarResposta(responsePayload);

            log.info("[Template] Resultado analise externa: {}", aprovado ? "APROVADO" : "REPROVADO");
            return aprovado;
        } catch (Exception ex) {
            log.error("[Template] Falha no fluxo de integracao externa", ex);
            return false;
        }
    }

    protected boolean validarSolicitacao(SolicitacaoCreditoRecord solicitacao) {
        return solicitacao != null
                && solicitacao.documento() != null
                && !solicitacao.documento().isBlank()
                && solicitacao.valor() != null;
    }

    protected abstract String montarRequisicao(SolicitacaoCreditoRecord solicitacao);

    protected abstract String enviarRequisicao(String payload);

    protected abstract boolean processarResposta(String resposta);
}