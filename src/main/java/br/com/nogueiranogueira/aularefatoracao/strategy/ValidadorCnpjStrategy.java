package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCnpjStrategy implements ValidadorDocumentoStrategy {

    private static final int[] PESOS_DIGITO_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_DIGITO_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    @Override
    public boolean suporta(TipoConta tipoConta) {
        return tipoConta == TipoConta.PJ;
    }

    @Override
    public boolean validar(String documento) {
        String cnpj = normalizar(documento);
        if (cnpj.length() != 14 || todosDigitosIguais(cnpj)) {
            return false;
        }

        int digito1 = calcularDigito(cnpj.substring(0, 12), PESOS_DIGITO_1);
        int digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, PESOS_DIGITO_2);

        return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);
    }

    private String normalizar(String documento) {
        if (documento == null) {
            return "";
        }
        return documento.replaceAll("\\D", "");
    }

    private boolean todosDigitosIguais(String cnpj) {
        return cnpj.chars().allMatch(c -> c == cnpj.charAt(0));
    }

    private int calcularDigito(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * pesos[i];
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}

