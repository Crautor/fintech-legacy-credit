package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;

public class SerasaSoapAdapter implements ServicoAnaliseRiscoExterno {

    private static final String SERASA_QA_ENDPOINT = "https://qa.serasa.com.br/ws/ConsultaCredito";

    @Override
    public boolean avaliarRisco(SolicitacaoCredito solicitacao) {
        System.out.println("[Adapter] Iniciando tradução do domínio para SOAP/XML...");
        String soapPayload = """
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
        """.formatted(solicitacao.getCliente(), solicitacao.getValor().toString(), solicitacao.getScore());

        // to be continued ...
        return false;
    }
}
