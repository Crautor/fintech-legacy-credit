package br.com.nogueiranogueira.aularefatoracao.service.factory;

import br.com.nogueiranogueira.aularefatoracao.domain.Cpf;
import br.com.nogueiranogueira.aularefatoracao.domain.Curp;
import br.com.nogueiranogueira.aularefatoracao.domain.Documento;
import br.com.nogueiranogueira.aularefatoracao.domain.Ssn;

public final class AnaliseFactory {

    private static final AnaliseStrategy.Brasil BRASIL = new AnaliseBrasilStrategy();
    private static final AnaliseStrategy.Mexico MEXICO = new AnaliseMexicoStrategy();
    private static final AnaliseStrategy.Eua EUA = new AnaliseEuaStrategy();

    private AnaliseFactory() {
    }

    public static AnaliseStrategy obterEstrategia(Documento documento) {
        if (documento == null) {
            throw new IllegalArgumentException("Documento nao pode ser nulo");
        }

        return switch (documento) {
            case Cpf ignored -> BRASIL;
            case Curp ignored -> MEXICO;
            case Ssn ignored -> EUA;
        };
    }

    private static final class AnaliseBrasilStrategy implements AnaliseStrategy.Brasil {
        @Override
        public String pais() {
            return "BR";
        }

        @Override
        public boolean analisar(Documento documento) {
            return documento.validar();
        }
    }

    private static final class AnaliseMexicoStrategy implements AnaliseStrategy.Mexico {
        @Override
        public String pais() {
            return "MX";
        }

        @Override
        public boolean analisar(Documento documento) {
            return documento.validar();
        }
    }

    private static final class AnaliseEuaStrategy implements AnaliseStrategy.Eua {
        @Override
        public String pais() {
            return "US";
        }

        @Override
        public boolean analisar(Documento documento) {
            return documento.validar();
        }
    }
}

