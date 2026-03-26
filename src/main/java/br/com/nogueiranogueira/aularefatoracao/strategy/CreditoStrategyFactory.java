package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.domain.Cpf;
import br.com.nogueiranogueira.aularefatoracao.domain.Curp;
import br.com.nogueiranogueira.aularefatoracao.domain.Documento;
import br.com.nogueiranogueira.aularefatoracao.domain.Ssn;
import org.springframework.stereotype.Component;

@Component
public class CreditoStrategyFactory {

    public static AnaliseStrategy obterStrategy(Documento documento){
        return switch (documento) {
            case Cpf cpf -> new AnaliseStrategyPF();
            case Curp curp -> new AnaliseStrategyCurp();
            case Ssn ssn -> new AnaliseStrategySsn();
        };
    }
}