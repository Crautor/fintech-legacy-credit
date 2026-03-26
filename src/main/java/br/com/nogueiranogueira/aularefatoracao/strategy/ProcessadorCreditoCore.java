package br.com.nogueiranogueira.aularefatoracao.strategy;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.com.nogueiranogueira.aularefatoracao.strategy.CreditoStrategyFactory;

@Service
public class ProcessadorCreditoCore {
    private static final Logger log = LoggerFactory.getLogger(ProcessadorCreditoCore.class);
    private final CreditoStrategyFactory factory;

    public ProcessadorCreditoCore(CreditoStrategyFactory factory) {
        this.factory = factory;
    }

    public boolean processar(SolicitacaoAnalise solicitacao) {
        log.info("Iniciando processamento de crédito para o cliente {} no país {}", solicitacao.cliente(), solicitacao.pais());

        return factory.getStrategy(solicitacao.pais())
                .map(strategy -> strategy.analisar(solicitacao))
                .orElseThrow(() -> {
                    log.error("Nenhuma estratégia de análise de crédito encontrada para o país: {}", solicitacao.pais());
                    return new IllegalArgumentException("País não suportado para análise de crédito: " + solicitacao.pais());
                });
    }
}