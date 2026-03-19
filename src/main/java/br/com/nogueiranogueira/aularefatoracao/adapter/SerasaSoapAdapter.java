package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@ConditionalOnProperty(name = "integracao.risco.tipo", havingValue = "soap", matchIfMissing = true)
public class SerasaSoapAdapter extends AnaliseRiscoTemplate {

    private static final String SERASA_QA_ENDPOINT = "https://qa.serasa.com.br/ws/ConsultaCredito";

    @Override
    protected String montarRequisicao(SolicitacaoCreditoRecord solicitacao) {
        return """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://serasa.com.br/ws">
                <soapenv:Header/>
                <soapenv:Body>
                    <ser:ConsultarRisco>
                        <documento>%s</documento>
                        <valorSolicitado>%s</valorSolicitado>
                        <scoreInterno>%d</scoreInterno>
                    </ser:ConsultarRisco>
                </soapenv:Body>
            </soapenv:Envelope>
        """.formatted(
                solicitacao.documento(),
                solicitacao.valor().toString(),
                solicitacao.score()
        );
    }

    @Override
    protected String enviarRequisicao(String payload) {
        System.out.println("[Adapter SOAP] Simulando envio para endpoint: " + SERASA_QA_ENDPOINT);
        System.out.println("Payload enviado:\n" + payload);
        return """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                <soapenv:Body>
                    <ConsultarRiscoResponse>
                        <statusConsulta>APROVADO_BAIXO_RISCO</statusConsulta>
                        <codigoRetorno>00</codigoRetorno>
                        <limiteSugerido>5000.00</limiteSugerido>
                    </ConsultarRiscoResponse>
                </soapenv:Body>
            </soapenv:Envelope>
        """;
    }

    @Override
    protected boolean processarResposta(String resposta) {
        System.out.println("[Adapter] Traduzindo resposta XML para domínio...");
        return resposta.contains("<statusConsulta>APROVADO");
    }
}