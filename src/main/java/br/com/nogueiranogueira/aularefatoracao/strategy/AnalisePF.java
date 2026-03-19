package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class AnalisePF implements SolicitacaoStrategy {

    private static final Logger log = LoggerFactory.getLogger(AnalisePF.class);

    @Override
    public boolean Analisar(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > 5000 && solicitacao.score() < 800) {
            log.warn("Reprovado: Valor alto para PF com score médio");
            return false;
        }

        // Refatoração do new Date().getDay() para API moderna do Java
        DayOfWeek diaSemana = LocalDate.now().getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            log.warn("Aprovação manual necessária no fim de semana");
            return false;
        }

        log.info("Aprovado PF");
        return true;
    }

    @Override
    public boolean Elegivel(SolicitacaoAnalise solicitacao) {
        return solicitacao.tipoConta() == TipoConta.PF;
    }
}