package br.com.nogueiranogueira.aularefatoracao.domain;

public record Ssn(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando SSN Americano (9 dígitos): " + valor);
        return valor != null && valor.length() == 9;
    }
}