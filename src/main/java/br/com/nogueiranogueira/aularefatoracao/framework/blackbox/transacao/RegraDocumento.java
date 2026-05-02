package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

/**
 * CÓDIGO-CLIENTE — Regra Black-box: Validação de Documento.
 *
 * <p>IMPLEMENTA a interface {@code RegraValidacao} (implements, não extends).</p>
 * <p>Valida se o documento tem o tamanho correto para o tipo (PF=11, PJ=14).</p>
 */
public class RegraDocumento implements RegraValidacao {

    @Override
    public ResultadoValidacao validar(Transacao transacao) {
        String doc = transacao.documento().replaceAll("[^0-9]", "");

        boolean valido = switch (transacao.tipo()) {
            case "PF" -> doc.length() == 11;
            case "PJ" -> doc.length() == 14;
            default -> false;
        };

        return valido
                ? ResultadoValidacao.sucesso()
                : ResultadoValidacao.falha("Documento inválido para tipo " + transacao.tipo()
                    + " (esperado " + (transacao.tipo().equals("PF") ? "11" : "14") + " dígitos)");
    }

    @Override
    public String descricao() {
        return "Validação de Documento (CPF/CNPJ)";
    }
}
