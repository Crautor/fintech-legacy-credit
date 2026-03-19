package br.com.nogueiranogueira.aularefatoracao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SolicitacaoCreditoIntegrationTest {

    private static final String API_CONTEXT = "/api";
    private static final String BASE_RESOURCE = "/api/solicitacoes";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveResponderSaude() throws Exception {
        mockMvc.perform(get(BASE_RESOURCE + "/saude").contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.mensagem").value("Aplicação funcionando corretamente"));
    }

    @Test
    void deveAnalisarSolicitacaoPjComDocumentoValido() throws Exception {
        mockMvc.perform(post(BASE_RESOURCE + "/analisar").contextPath(API_CONTEXT)
                        .param("cliente", "Empresa ABC LTDA")
                        .param("documento", "04.252.011/0001-10")
                        .param("valor", "25000")
                        .param("score", "750")
                        .param("negativado", "false")
                        .param("tipoConta", "PJ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("Empresa ABC LTDA"))
                .andExpect(jsonPath("$.documento").value("04.252.011/0001-10"))
                .andExpect(jsonPath("$.aprovado").value(true));
    }

    @Test
    void deveReprovarQuandoDocumentoForInvalido() throws Exception {
        mockMvc.perform(post(BASE_RESOURCE + "/analisar").contextPath(API_CONTEXT)
                        .param("cliente", "Joao Silva")
                        .param("documento", "11111111111")
                        .param("valor", "2000")
                        .param("score", "700")
                        .param("negativado", "false")
                        .param("tipoConta", "PF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aprovado").value(false));
    }

    @Test
    void deveRetornarBadRequestQuandoTipoContaInvalido() throws Exception {
        mockMvc.perform(post(BASE_RESOURCE + "/analisar").contextPath(API_CONTEXT)
                        .param("cliente", "Cliente Teste")
                        .param("documento", "52998224725")
                        .param("valor", "1000")
                        .param("score", "700")
                        .param("negativado", "false")
                        .param("tipoConta", "XPTO"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Tipo de conta inválido. Use PF ou PJ."));
    }

    @Test
    void deveProcessarLote() throws Exception {
        mockMvc.perform(post(BASE_RESOURCE + "/processar-lote").contextPath(API_CONTEXT)
                        .contentType("application/json")
                        .content("[\"Cliente1\", \"Cliente2\", \"Cliente3\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Lote processado com sucesso"))
                .andExpect(jsonPath("$.totalClientes").value("3"));
    }
}
