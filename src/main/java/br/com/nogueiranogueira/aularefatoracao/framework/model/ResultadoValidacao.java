package br.com.nogueiranogueira.aularefatoracao.framework.model;

/**
 * Record que encapsula o resultado de uma validação de transação.
 * Inclui factory methods para facilitar a criação.
 */
public record ResultadoValidacao(
        boolean aprovado,
        String motivo
) {

    /** Cria um resultado aprovado. */
    public static ResultadoValidacao sucesso() {
        return new ResultadoValidacao(true, "Transação aprovada");
    }

    /** Cria um resultado reprovado com o motivo informado. */
    public static ResultadoValidacao falha(String motivo) {
        return new ResultadoValidacao(false, motivo);
    }

    @Override
    public String toString() {
        return aprovado ? "✅ APROVADO" : "❌ REPROVADO — " + motivo;
    }
}
