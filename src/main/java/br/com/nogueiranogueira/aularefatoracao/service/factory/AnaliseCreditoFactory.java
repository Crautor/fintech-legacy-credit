package br.com.nogueiranogueira.aularefatoracao.service.factory;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategy;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPF;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPJ;

public class AnaliseCreditoFactory {
    // Java 21: Switch Expression com "Arrow syntax" (->)
    public static AnaliseStrategy obterEstrategia(TipoConta tipo) {
        return switch (tipo) {
            case PF -> new AnaliseStrategyPF();
            case PJ -> new AnaliseStrategyPJ();
            default -> throw new IllegalArgumentException("Tipo de conta não suportado: " + tipo);
        };
    }
}