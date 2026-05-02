package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.domain.Cpf;
import br.com.nogueiranogueira.aularefatoracao.domain.Curp;
import br.com.nogueiranogueira.aularefatoracao.domain.Documento;
import br.com.nogueiranogueira.aularefatoracao.domain.Ssn;
import br.com.nogueiranogueira.aularefatoracao.dto.Pais;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditoStrategyFactory {

    public static AnaliseStrategy obterStrategy(Documento documento){
        return switch (documento) {
            case Cpf cpf -> new AnaliseStrategyPF();
            case Curp curp -> new AnaliseStrategyCurp();
            case Ssn ssn -> new AnaliseStrategySsn();
        };
    }

    public Optional<AnaliseStrategy> getStrategy(Pais pais) {
        return switch (pais) {
            case BR -> Optional.of(new AnaliseStrategyPF());
            case US -> Optional.of(new AnaliseStrategySsn());
        };
    }
}