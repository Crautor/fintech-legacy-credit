package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.Pais;
import br.com.nogueiranogueira.aularefatoracao.strategy.CreditoStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CreditoStrategyFactory {
    private final Map<Pais, CreditoStrategy> strategyMap;

    public CreditoStrategyFactory(List<CreditoStrategy> strategies) {
        strategyMap = new EnumMap<>(Pais.class);
        strategies.forEach(s -> strategyMap.put(Pais.valueOf(s.getPais()), s));
    }

    public Optional<CreditoStrategy> getStrategy(Pais pais) {
        return Optional.ofNullable(strategyMap.get(pais));
    }
}