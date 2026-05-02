package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio;

/**
 * CÓDIGO-CLIENTE — Persistência em disco Black-box.
 *
 * <p>IMPLEMENTA a interface {@code PersistenciaRelatorio} (implements, não extends).</p>
 * <p>Simula o salvamento do relatório em disco.</p>
 *
 * <p><b>Vantagem Black-box:</b> poderia ser facilmente substituída por
 * {@code PersistenciaCloud}, {@code PersistenciaBancoDados}, etc.,
 * sem alterar o FormatadorPdf nem o Engine.</p>
 */
public class PersistenciaDisco implements PersistenciaRelatorio {

    @Override
    public void salvar(String conteudo, String nomeArquivo) {
        System.out.println("[PersistenciaDisco] Salvando arquivo: /reports/" + nomeArquivo);
        System.out.println("[PersistenciaDisco] Tamanho: " + conteudo.length() + " caracteres");
        System.out.println("[PersistenciaDisco] ✔ Arquivo salvo com sucesso!");
    }
}
