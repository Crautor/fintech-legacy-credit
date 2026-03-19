package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import br.com.nogueiranogueira.aularefatoracao.adapter.ServicoAnaliseRisco;
import br.com.nogueiranogueira.aularefatoracao.strategy.SolicitacaoStrategy;
import br.com.nogueiranogueira.aularefatoracao.validation.ValidadorDocumentoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnaliseCreditoService {

    private final List<SolicitacaoStrategy> strategies;
    private final ValidadorDocumentoFactory validadorDocumentoFactory;
    private final ServicoAnaliseRisco servicoAnaliseRisco;

    public boolean analisarSolicitacao(SolicitacaoAnalise solicitacao) {
        log.info("Iniciando análise para: {}", solicitacao.cliente());

        boolean documentoValido = validadorDocumentoFactory
                .obterValidador(solicitacao.tipoConta())
                .validar(solicitacao.documento());
                
        if (!documentoValido) {
            log.warn("Documento inválido. Solicitação reprovada.");
            return false;
        }

        if (solicitacao.valor() <= 0) {
            log.warn("Valor inválido para a solicitação.");
            return false;
        }
        if (solicitacao.negativado()) {
            log.warn("Cliente negativado. Solicitação reprovada.");
            return false;
        }
        if (solicitacao.score() <= 500) {
            log.warn("Score muito baixo. Solicitação reprovada.");
            return false;
        }

        try {
            log.info("Consultando Bureau de Crédito Externo...");
            
            SolicitacaoCreditoRecord solicitacaoRecord = new SolicitacaoCreditoRecord(
                    solicitacao.cliente(),
                    solicitacao.documento(),
                    BigDecimal.valueOf(solicitacao.valor()),
                    solicitacao.score(),
                    solicitacao.negativado(),
                    solicitacao.tipoConta()
            );
            
            boolean aprovadoExternamente = servicoAnaliseRisco.avaliarCredito(solicitacaoRecord);
            if (!aprovadoExternamente) {
                log.warn("Reprovado na análise de risco externa (Adapter).");
                return false;
            }
        } catch (Exception e) {
            log.error("Erro na comunicação com o Bureau de Crédito", e);
            return false;
        }

        return strategies.stream()
                .filter(strategy -> strategy.Elegivel(solicitacao))
                .findFirst()
                .map(strategy -> strategy.Analisar(solicitacao))
                .orElseGet(() -> {
                    log.error("Nenhuma estratégia encontrada para o tipo de conta: {}", solicitacao.tipoConta());
                    return false;
                });
    }

    public void processarLote(List<SolicitacaoAnalise> solicitacoes) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (SolicitacaoAnalise solicitacao : solicitacoes) {
                executor.submit(() -> analisarSolicitacao(solicitacao));
            }

        }
    }

    public void analisarSolicitacao(String clienteAprovado, int i, int i1, boolean b, String pf) {
    }
}