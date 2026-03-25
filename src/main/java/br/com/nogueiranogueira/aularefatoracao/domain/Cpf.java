package br.com.nogueiranogueira.aularefatoracao.domain;

public record Cpf(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando CPF (11 digitos): " + valor);
        return valor != null && valor.length() == 11;
    }
}
