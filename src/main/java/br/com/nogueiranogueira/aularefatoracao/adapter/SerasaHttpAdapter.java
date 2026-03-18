package br.com.nogueiranogueira.aularefatoracao.adapter;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;

public class SerasaHttpAdapter implements ServicoAnaliseRiscoExterno {

    private static final String SERASA_QA_ENDPOINT = "https://qa.serasa.com.br/api/credito";

    @Override
    public boolean avaliarRisco(SolicitacaoCredito solicitacao) {

        System.out.println("[Adapter-HTTP] Iniciando tradução do domínio para JSON...");
        String jsonPayload = """
            {
                "documento": "%s",
                "valorSolicitado": "%s",
                "scoreInterno": %d
            }
        """.formatted(
                solicitacao.getCliente(),
                solicitacao.getValor().toString(),
                solicitacao.getScore()
        );

        try {
            System.out.println("[Adapter-HTTP] Simulando envio para API REST...");
            System.out.println("Payload enviado:\n" + jsonPayload);

            // 2. SIMULAÇÃO (sem HTTP real)
            String jsonRespostaApi = simularRespostaDaApiExterna();

            // 3. TRADUÇÃO DE VOLTA (JSON -> domínio)
            System.out.println("[Adapter-HTTP] Traduzindo resposta JSON...");
            return analisarRespostaJson(jsonRespostaApi);

        } catch (Exception e) {
            System.err.println("Erro na integração com API HTTP: " + e.getMessage());
            return false;
        }
    }

    private String simularRespostaDaApiExterna() {
        return """
            {
                "status": "APROVADO_BAIXO_RISCO",
                "codigo": "00",
                "limiteSugerido": 5000.00
            }
        """;
    }

    private boolean analisarRespostaJson(String json) {
        return json.contains("\"status\":\"APROVADO");
    }
}