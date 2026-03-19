package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCpfStrategy implements ValidadorDocumentoStrategy {

    @Override
    public boolean suporta(TipoConta tipoConta) {
        return tipoConta == TipoConta.PF;
    }

    @Override
    public boolean validar(String documento) {
        String cpf = normalizar(documento);
        if (cpf.length() != 11 || todosDigitosIguais(cpf)) {
            return false;
        }

        int digito1 = calcularDigito(cpf.substring(0, 9), 10);
        int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, 11);

        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    private String normalizar(String documento) {
        if (documento == null) {
            return "";
        }
        return documento.replaceAll("\\D", "");
    }

    private boolean todosDigitosIguais(String cpf) {
        return cpf.chars().allMatch(c -> c == cpf.charAt(0));
    }

    private int calcularDigito(String base, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * (pesoInicial - i);
        }

        int resto = 11 - (soma % 11);
        return resto >= 10 ? 0 : resto;
    }
}

