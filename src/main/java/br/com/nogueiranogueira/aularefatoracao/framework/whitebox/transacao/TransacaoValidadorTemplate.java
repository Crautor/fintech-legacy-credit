package br.com.nogueiranogueira.aularefatoracao.framework.whitebox.transacao;

import br.com.nogueiranogueira.aularefatoracao.framework.model.ResultadoValidacao;
import br.com.nogueiranogueira.aularefatoracao.framework.model.Transacao;

/**
 * WHITE-BOX FRAMEWORK — Validação de Transações via Template Method.
 *
 * <p>Esta classe abstrata define o <b>esqueleto do algoritmo</b> de validação.
 * O fluxo é controlado pelo framework (IoC — Inversão de Controle).</p>
 *
 * <p>O código-cliente ESTENDE esta classe e sobrescreve os métodos abstratos
 * (hook methods) para fornecer regras de negócio específicas.</p>
 *
 * <p><b>Padrão:</b> Template Method</p>
 * <p><b>Extensão:</b> via herança (extends)</p>
 * <p><b>Acoplamento:</b> alto — subclasses dependem da superclasse</p>
 */
public abstract class TransacaoValidadorTemplate {

    /**
     * TEMPLATE METHOD — define o algoritmo de validação.
     * Marcado como {@code final} para que subclasses NÃO alterem a ordem.
     *
     * <p><b>Inversão de Controle:</b> o framework decide quando chamar cada passo.
     * "Don't call us, we'll call you."</p>
     */
    public final ResultadoValidacao validar(Transacao transacao) {
        System.out.println("\n[White-box Template] Iniciando validação para: " + transacao);

        // Passo 1: Validar documento
        if (!validarDocumento(transacao)) {
            System.out.println("[White-box Template] ❌ Falha na validação do documento");
            return ResultadoValidacao.falha("Documento inválido para tipo " + transacao.tipo());
        }
        System.out.println("[White-box Template] ✔ Documento válido");

        // Passo 2: Validar valor
        if (!validarValor(transacao)) {
            System.out.println("[White-box Template] ❌ Falha na validação do valor");
            return ResultadoValidacao.falha("Valor fora dos limites permitidos");
        }
        System.out.println("[White-box Template] ✔ Valor dentro do limite");

        // Passo 3: Validar regras específicas de negócio
        if (!validarRegrasEspecificas(transacao)) {
            System.out.println("[White-box Template] ❌ Falha nas regras específicas");
            return ResultadoValidacao.falha("Regra de negócio específica violada");
        }
        System.out.println("[White-box Template] ✔ Regras específicas OK");

        System.out.println("[White-box Template] ✅ Validação concluída com sucesso!");
        return ResultadoValidacao.sucesso();
    }

    // ─── Hook Methods — subclasses DEVEM implementar ───────────────

    /** Valida o formato e tipo do documento da transação. */
    protected abstract boolean validarDocumento(Transacao transacao);

    /** Valida se o valor está dentro dos limites permitidos. */
    protected abstract boolean validarValor(Transacao transacao);

    /** Valida regras de negócio específicas (score, negativação, etc). */
    protected abstract boolean validarRegrasEspecificas(Transacao transacao);
}
