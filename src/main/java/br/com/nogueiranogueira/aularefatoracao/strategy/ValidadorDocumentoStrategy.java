package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;

public interface ValidadorDocumentoStrategy {
    boolean suporta(TipoConta tipoConta);

    boolean validar(String documento);
}

