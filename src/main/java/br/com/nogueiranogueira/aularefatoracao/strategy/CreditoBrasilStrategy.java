package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.Pais;
import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class CreditoBrasilStrategy implements CreditoStrategy {
    private static final Logger log = LoggerFactory.getLogger(CreditoBrasilStrategy.class);

    @Override
    public boolean analisar(SolicitacaoAnalise solicitacao) {
        log.info("Aplicando regras de análise de crédito para o Brasil (BR).");

        if (solicitacao.negativado()) {
            log.warn("Reprovado (BR): Cliente negativado.");
            return false;
        }
        if (solicitacao.score() < 500) {
            log.warn("Reprovado (BR): Score abaixo do mínimo (500).");
            return false;
        }

        return switch (solicitacao.tipoConta()) {
            case PF -> analisarPF(solicitacao);
            case PJ -> analisarPJ(solicitacao);
        };
    }

    private boolean analisarPF(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > 5000 && solicitacao.score() < 800) {
            log.warn("Reprovado (BR-PF): Valor alto para score médio.");
            return false;
        }
        DayOfWeek diaSemana = LocalDate.now().getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            log.warn("Reprovado (BR-PF): Aprovação manual necessária no fim de semana.");
            return false;
        }
        log.info("Aprovado (BR-PF)");
        return true;
    }

    private boolean analisarPJ(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > 50000 && solicitacao.score() < 700) {
            log.warn("Reprovado (BR-PJ): Valor alto para score médio.");
            return false;
        }
        log.info("Aprovado (BR-PJ)");
        return true;
    }

}