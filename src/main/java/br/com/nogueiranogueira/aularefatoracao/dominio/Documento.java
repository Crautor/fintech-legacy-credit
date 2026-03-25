package br.com.nogueiranogueira.aularefatoracao.dominio;

// A sealed interface define exatamente quais records podem implementá-la
public sealed interface Documento permits Cpf, Curp, Ssn {
    String valor();
    boolean validar();
}

// Implementações usando Records no mesmo ficheiro (não levam "public")
record Cpf(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando CPF (11 digitos): " + valor);
        return valor != null && valor.length() == 11;
    }
}

record Curp(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando CURP Mexicano (18 digitos): " + valor);
        return valor != null && valor.length() == 18;
    }
}

record Ssn(String valor) implements Documento {
    @Override
    public boolean validar() {
        System.out.println("Validando SSN Americano (9 dígitos): " + valor);
        return valor != null && valor.length() == 9;
    }
}