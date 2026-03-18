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
        """.formatted(
                solicitacao.getCliente(),
                solicitacao.getValor().toString(),
                solicitacao.getScore()
        );

        try {
            // 2. SIMULAÇÃO DA CHAMADA (didático)
            System.out.println("[Adapter] Simulando envio para API SOAP...");
            System.out.println("Payload enviado:\n" + soapPayload);

            String xmlRespostaLegada = simularRespostaDaApiExterna();

            // 3. TRADUÇÃO DE VOLTA (XML -> domínio)
            System.out.println("[Adapter] Traduzindo resposta XML para domínio...");
            return analisarXmlResposta(xmlRespostaLegada);

        } catch (Exception e) {
            System.err.println("Erro na integração com o sistema legado: " + e.getMessage());
            return false;
        }
    }

    private String simularRespostaDaApiExterna() {
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

    private boolean analisarXmlResposta(String xml) {
        return xml.contains("<statusConsulta>APROVADO");
    }
}