package br.com.nogueiranogueira.aularefatoracao.domain;

public record Curp(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando CURP Mexicano (18 digitos): " + valor);
        return valor != null && valor.length() == 18;
    }
}