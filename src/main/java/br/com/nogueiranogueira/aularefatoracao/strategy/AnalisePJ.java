package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AnalisePJ implements SolicitacaoStrategy {

    private static final Logger log = LoggerFactory.getLogger(AnalisePJ.class);

    @Override
    public boolean Analisar(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > 50000 && solicitacao.score() < 700) {
            log.warn("Reprovado: Risco PJ");
            return false;
        }
        log.info("Aprovado PJ");
        return true;
    }

    @Override
    public boolean Elegivel(SolicitacaoAnalise solicitacao) {
        return solicitacao.tipoConta() == TipoConta.PJ;
    }
}