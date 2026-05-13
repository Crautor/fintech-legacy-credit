package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;
import com.validador.core.domain.Cnpj;
import com.validador.core.domain.Cpf;
import com.validador.core.domain.Documento;
import com.validador.core.service.ValidadorDocumentoService;

/**
 * CÓDIGO-CLIENTE — Regra Black-box: Validação de Documento.
 *
 * <p>IMPLEMENTA a interface {@code RegraValidacao} (implements, não extends).</p>
 * <p>Delega a validação ao {@link ValidadorDocumentoService} do validator-core (Repo 1),
 * demonstrando a integração entre os dois repositórios.</p>
 */
public class RegraDocumento implements RegraValidacao {

    private final ValidadorDocumentoService validadorService = new ValidadorDocumentoService();

    @Override
    public ResultadoValidacao validar(Transacao transacao) {
        try {
            Documento documento = switch (transacao.tipo()) {
                case "PF" -> new Cpf(transacao.documento());
                case "PJ" -> new Cnpj(transacao.documento());
                default -> throw new IllegalArgumentException("Tipo desconhecido: " + transacao.tipo());
            };

            com.validador.core.service.ResultadoValidacao resultadoCore = validadorService.validar(documento);

            return resultadoCore.valido()
                    ? ResultadoValidacao.sucesso()
                    : ResultadoValidacao.falha(resultadoCore.mensagem());
        } catch (IllegalArgumentException e) {
            return ResultadoValidacao.falha("Documento inválido: " + e.getMessage());
        }
    }

    @Override
    public String descricao() {
        return "Validação de Documento via validator-core (CPF/CNPJ)";
    }
}
