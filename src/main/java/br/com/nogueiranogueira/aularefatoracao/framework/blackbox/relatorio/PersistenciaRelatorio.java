package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio;

/**
 * BLACK-BOX FRAMEWORK — Interface de persistência de relatório.
 *
 * <p>Strategy separada para definir ONDE o relatório será salvo
 * (disco, cloud, banco de dados, etc).</p>
 *
 * <p>O código-cliente IMPLEMENTA esta interface para fornecer
 * a estratégia de persistência desejada.</p>
 *
 * <p><b>Vantagem Black-box:</b> formatação e persistência são
 * estratégias independentes, combinadas livremente.</p>
 */
public interface PersistenciaRelatorio {

    /**
     * Salva o conteúdo do relatório no destino configurado.
     *
     * @param conteudo o relatório completo formatado
     * @param nomeArquivo nome sugerido para o arquivo
     */
    void salvar(String conteudo, String nomeArquivo);
}
