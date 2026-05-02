package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

import java.util.List;

/**
 * BLACK-BOX FRAMEWORK — Motor (Engine) de validação de transações.
 *
 * <p>Esta classe funciona como uma <b>caixa-preta</b>: o cliente não precisa
 * conhecer o código interno. Basta injetar as regras via construtor (DI).</p>
 *
 * <p><b>Inversão de Controle:</b> o engine decide quando e em qual ordem
 * chamar cada regra. "Don't call us, we'll call you."</p>
 *
 * <p><b>Vantagens sobre White-box:</b></p>
 * <ul>
 *   <li>Composição sobre herança — regras são combinadas livremente</li>
 *   <li>Menos acoplamento — regras não dependem umas das outras</li>
 *   <li>Mais testável — cada regra pode ser testada isoladamente</li>
 *   <li>Mais flexível — adicionar/remover regras sem alterar código existente</li>
 * </ul>
 */
public class TransacaoValidadorEngine {

    private final List<RegraValidacao> regras;

    /**
     * Construtor com injeção de dependência.
     * As regras são fornecidas externamente (composição, não herança).
     *
     * @param regras lista de regras de validação a serem executadas
     */
    public TransacaoValidadorEngine(List<RegraValidacao> regras) {
        this.regras = regras;
    }

    /**
     * Executa todas as regras de validação sobre a transação.
     *
     * <p><b>IoC em ação:</b> o engine controla o fluxo e decide
     * quando chamar cada regra do código-cliente.</p>
     *
     * @param transacao a transação a ser validada
     * @return resultado da validação (para na primeira falha)
     */
    public ResultadoValidacao validar(Transacao transacao) {
        System.out.println("\n[Black-box Engine] Iniciando validação para: " + transacao);
        System.out.println("[Black-box Engine] Regras registradas: " + regras.size());

        for (RegraValidacao regra : regras) {
            System.out.println("[Black-box Engine] Executando regra: " + regra.descricao());

            ResultadoValidacao resultado = regra.validar(transacao);

            if (!resultado.aprovado()) {
                System.out.println("[Black-box Engine] ❌ Falha na regra: " + regra.descricao());
                return resultado;
            }
            System.out.println("[Black-box Engine] ✔ Regra OK: " + regra.descricao());
        }

        System.out.println("[Black-box Engine] ✅ Todas as regras passaram!");
        return ResultadoValidacao.sucesso();
    }
}
