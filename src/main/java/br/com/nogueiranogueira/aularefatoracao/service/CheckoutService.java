package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.service.factory.PagamentoFactory;
import br.com.nogueiranogueira.aularefatoracao.service.factory.PagamentoStrategy;

public class CheckoutService {

    public void pagar(double valor, String metodo) {

        System.out.println("=== Iniciando processamento de pagamento ===");

        // Obtém a estratégia correta através da Factory
        PagamentoStrategy strategy = PagamentoFactory.obterEstrategia(metodo);
        
        // Executa o pagamento (polimorfismo)
        strategy.pagar(valor);

        System.out.println("=== Finalizando transação ===");
    }
}