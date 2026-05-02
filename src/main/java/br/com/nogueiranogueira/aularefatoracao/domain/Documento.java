package br.com.nogueiranogueira.aularefatoracao.domain;

public sealed interface Documento permits Cpf, Curp, Ssn {
    String valor();
    boolean validar();
}