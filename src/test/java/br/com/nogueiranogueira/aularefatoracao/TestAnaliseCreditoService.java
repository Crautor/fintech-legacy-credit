package br.com.nogueiranogueira.aularefatoracao;

import br.com.nogueiranogueira.aularefatoracao.adapter.ServicoAnaliseRisco;
import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.service.AnaliseCreditoService;
import br.com.nogueiranogueira.aularefatoracao.strategy.SolicitacaoStrategy;
import br.com.nogueiranogueira.aularefatoracao.strategy.ValidadorDocumentoFactory;
import br.com.nogueiranogueira.aularefatoracao.strategy.ValidadorDocumentoStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestAnaliseCreditoService {

    private static final String CPF_VALIDO = "52998224725";
    private static final String CNPJ_VALIDO = "04252011000110";

    private AnaliseCreditoService service;
    private ValidadorDocumentoFactory validadorDocumentoFactory;
    private ValidadorDocumentoStrategy validadorPf;
    private ValidadorDocumentoStrategy validadorPj;
    private ServicoAnaliseRisco servicoAnaliseRisco;
    private SolicitacaoStrategy strategyPf;
    private SolicitacaoStrategy strategyPj;

    @BeforeEach
    void setup() {
        validadorDocumentoFactory = mock(ValidadorDocumentoFactory.class);
        validadorPf = mock(ValidadorDocumentoStrategy.class);
        validadorPj = mock(ValidadorDocumentoStrategy.class);
        servicoAnaliseRisco = mock(ServicoAnaliseRisco.class);
        strategyPf = mock(SolicitacaoStrategy.class);
        strategyPj = mock(SolicitacaoStrategy.class);

        when(validadorDocumentoFactory.obterValidador(TipoConta.PF)).thenReturn(validadorPf);
        when(validadorDocumentoFactory.obterValidador(TipoConta.PJ)).thenReturn(validadorPj);
        when(validadorPf.validar(any())).thenReturn(true);
        when(validadorPj.validar(any())).thenReturn(true);
        when(servicoAnaliseRisco.avaliarCredito(any())).thenReturn(true);

        when(strategyPf.Elegivel(any())).thenAnswer(invocation ->
                ((SolicitacaoAnalise) invocation.getArgument(0)).tipoConta() == TipoConta.PF
        );
        when(strategyPj.Elegivel(any())).thenAnswer(invocation ->
                ((SolicitacaoAnalise) invocation.getArgument(0)).tipoConta() == TipoConta.PJ
        );
        when(strategyPf.Analisar(any())).thenReturn(true);
        when(strategyPj.Analisar(any())).thenReturn(true);

        service = new AnaliseCreditoService(
                List.of(strategyPf, strategyPj),
                validadorDocumentoFactory,
                servicoAnaliseRisco
        );
    }

    @Test
    void deveReprovarQuandoDocumentoInvalido() {
        when(validadorPf.validar("11111111111")).thenReturn(false);

        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Joao Silva", "11111111111", 2000.0, 700, false, TipoConta.PF
        );

        assertFalse(service.analisarSolicitacao(solicitacao));
    }

    @Test
    void deveReprovarQuandoValorInvalido() {
        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Joao Silva", CPF_VALIDO, -100.0, 700, false, TipoConta.PF
        );

        assertFalse(service.analisarSolicitacao(solicitacao));
    }

    @Test
    void deveReprovarQuandoClienteNegativado() {
        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Maria Santos", CPF_VALIDO, 1500.0, 700, true, TipoConta.PF
        );

        assertFalse(service.analisarSolicitacao(solicitacao));
    }

    @Test
    void deveReprovarQuandoScoreAbaixoDoMinimo() {
        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Pedro Costa", CPF_VALIDO, 1500.0, 400, false, TipoConta.PF
        );

        assertFalse(service.analisarSolicitacao(solicitacao));
    }

    @Test
    void deveReprovarQuandoIntegracaoExternaReprovar() {
        when(servicoAnaliseRisco.avaliarCredito(any())).thenReturn(false);

        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Empresa XYZ", CNPJ_VALIDO, 30000.0, 800, false, TipoConta.PJ
        );

        assertFalse(service.analisarSolicitacao(solicitacao));
    }

    @Test
    void deveAprovarQuandoTudoValido() {
        SolicitacaoAnalise solicitacao = new SolicitacaoAnalise(
                "Empresa ABC", CNPJ_VALIDO, 30000.0, 800, false, TipoConta.PJ
        );

        assertTrue(service.analisarSolicitacao(solicitacao));
    }
}