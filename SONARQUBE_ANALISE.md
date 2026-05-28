# Análise de Qualidade — SonarQube

## Comparativo: Primeiro Commit vs. Último Commit

| Métrica | Rodada 1 (primeiro commit) | Rodada 2 (último commit) |
|---|---|---|
| Lines of Code | 323 | 1.7k |
| Quality Gate | Passed ✅ | Failed ❌ |
| Security | 0 issues (A) | 0 issues (A) |
| Reliability | 2 issues (C) | 0 issues (A) ✅ |
| Maintainability | 36 issues (A) | 148 issues (A) |
| Dívida Técnica | — | 2 dias e 7 horas |
| Debt Ratio | — | 2.8% |
| Security Hotspot | 1 (E) | 0 (A) ✅ |
| Coverage | 0.0% (92 linhas) | 0.0% (640 linhas) |
| Duplications | 0.0% | 0.0% |

## Observações

- O projeto cresceu **~5x** em linhas de código (323 → 1.7k)
- **Reliability** melhorou de C → A (2 bugs eliminados)
- **Security Hotspot** resolvido: E → A
- A densidade de issues de Maintainability caiu proporcionalmente ao crescimento do código
- O Quality Gate falhou na Rodada 2 por política de "New Code" (7 novas issues introduzidas no último commit)

## Hashes

| Rodada | Commit | Descrição |
|---|---|---|
| Rodada 1 | `e9ac940` | Initial commit |
| Rodada 2 | `c8401c3` | Integration.md (último commit) |
