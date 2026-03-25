package br.com.nogueiranogueira.aularefatoracao.service.factory;

import br.com.nogueiranogueira.aularefatoracao.domain.Documento;

public sealed interface AnaliseStrategy
        permits AnaliseStrategy.Brasil,
                AnaliseStrategy.Mexico,
                AnaliseStrategy.Eua {

    String pais();
    boolean analisar(Documento documento);

    non-sealed interface Brasil extends AnaliseStrategy {}
    non-sealed interface Mexico extends AnaliseStrategy {}
    non-sealed interface Eua extends AnaliseStrategy {}
}
