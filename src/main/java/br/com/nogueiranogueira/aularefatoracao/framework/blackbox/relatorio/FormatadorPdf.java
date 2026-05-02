package br.com.nogueiranogueira.aularefatoracao.framework.blackbox.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CÓDIGO-CLIENTE — Formatador PDF Black-box.
 *
 * <p>IMPLEMENTA a interface {@code FormatadorRelatorio} (implements, não extends).</p>
 * <p>Formata os dados como um documento PDF visual.</p>
 */
public class FormatadorPdf implements FormatadorRelatorio {

    @Override
    public String formatarCabecalho() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return "╔══════════════════════════════════════════════╗\n"
             + "║     RELATÓRIO DE ANÁLISE DE CRÉDITO (PDF)    ║\n"
             + "║     Fintech Legacy Credit — Black-box        ║\n"
             + "║     Gerado em: " + dataHora + "         ║\n"
             + "╠══════════════════════════════════════════════╣\n"
             + "║  DOCUMENTO       │  VALOR     │  STATUS      ║\n"
             + "╠══════════════════════════════════════════════╣\n";
    }

    @Override
    public String formatarDados(List<String> dados) {
        StringBuilder corpo = new StringBuilder();
        for (String dado : dados) {
            corpo.append("║  ").append(String.format("%-44s", dado.replace(" - ", "  │  "))).append("║\n");
        }
        return corpo.toString();
    }

    @Override
    public String formatarRodape() {
        return "╠══════════════════════════════════════════════╣\n"
             + "║  Gerado via Framework Black-box (Strategy)   ║\n"
             + "║  © 2026 Fintech Legacy Credit                ║\n"
             + "╚══════════════════════════════════════════════╝\n";
    }
}
