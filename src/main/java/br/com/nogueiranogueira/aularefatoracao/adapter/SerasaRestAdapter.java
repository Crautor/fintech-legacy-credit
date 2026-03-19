package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "integracao.risco.tipo", havingValue = "rest")
public class SerasaRestAdapter extends AnaliseRiscoTemplate {

    @Override
    protected String montarRequisicao(SolicitacaoCreditoRecord solicitacao) {
        return """
                {
                  "documento": "%s",
                  "valorSolicitado": %s,
                  "scoreInterno": %d,
                  "tipoConta": "%s"
                }
                """.formatted(
                solicitacao.documento(),
                solicitacao.valor(),
                solicitacao.score(),
                solicitacao.tipo()
        );
    }

    @Override
    protected String enviarRequisicao(String payload) {
        System.out.println("[Adapter REST] Simulando POST para endpoint REST...");
        System.out.println("Payload enviado:\n" + payload);
        return """
                {
                  "statusConsulta": "APROVADO_BAIXO_RISCO",
                  "codigoRetorno": "00",
                  "limiteSugerido": 5000.00
                }
                """;
    }

    @Override
    protected boolean processarResposta(String resposta) {
        return resposta.contains("\"statusConsulta\": \"APROVADO");
    }
}

