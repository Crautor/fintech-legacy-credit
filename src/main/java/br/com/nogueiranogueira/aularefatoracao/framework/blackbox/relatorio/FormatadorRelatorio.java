package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio;

import java.util.List;

/**
 * BLACK-BOX FRAMEWORK — Interface de formatação de relatório.
 *
 * <p>O código-cliente IMPLEMENTA esta interface ({@code implements FormatadorRelatorio})
 * para definir como os dados devem ser formatados (PDF, CSV, XML, etc).</p>
 *
 * <p><b>Padrão:</b> Strategy</p>
 * <p><b>Extensão:</b> via composição (implements)</p>
 */
public interface FormatadorRelatorio {

    /** Gera o cabeçalho no formato desejado. */
    String formatarCabecalho();

    /** Formata os dados no formato desejado. */
    String formatarDados(List<String> dados);

    /** Gera o rodapé no formato desejado. */
    String formatarRodape();
}
