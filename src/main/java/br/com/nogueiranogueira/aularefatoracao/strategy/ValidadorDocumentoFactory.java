package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidadorDocumentoFactory {

    private final List<ValidadorDocumentoStrategy> validadores;

    public ValidadorDocumentoFactory(List<ValidadorDocumentoStrategy> validadores) {
        this.validadores = validadores;
    }

    public ValidadorDocumentoStrategy obterValidador(TipoConta tipoConta) {
        return validadores.stream()
                .filter(validador -> validador.suporta(tipoConta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum validador encontrado para tipo de conta: " + tipoConta));
    }
}

