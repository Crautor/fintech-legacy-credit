# Análise de Qualidade — SonarQube e MetricsTree

## 7.1 Tabela Comparativa CBO e LCOM

> Métricas calculadas via análise estática do código-fonte.
> **CBO** (Coupling Between Objects): acoplamento — ideal ≤ 5. Acima de 10 indica acoplamento excessivo.
> **LCOM** (Lack of Cohesion in Methods): coesão — ideal = 0. Acima de 1 indica baixa coesão.

| Classe | CBO v1 | CBO v2 | LCOM v1 | LCOM v2 | Observação |
|---|---|---|---|---|---|
| AnaliseCreditoService | 0 | 7 | 0 | 1 | Ganhou dependências via injeção (DI), mas processarLote não acessa campos diretamente |
| SolicitacaoCreditoController | 2 | 11 | 1 | 2 | Cresceu de 1 para 3 dependências; métodos usam subconjuntos diferentes dos serviços |
| ProcessadorVendaService | 0 | 1 | 0 | 0 | Pequena melhora: extraiu CalculadoraImposto |
| SolicitacaoCredito | 0 | 0 | 0 | 0 | Entidade de dados estável |
| SolicitacaoCreditoRepository | 1 | 1 | 0 | 0 | Interface JPA — sem alteração |

### Interpretação

- **AnaliseCreditoService**: o CBO subiu de 0 → 7 porque a classe original era uma *God Method* sem dependências injetadas. A refatoração introduziu DI correta, o que é positivo mesmo aumentando o CBO.
- **SolicitacaoCreditoController**: CBO 2 → 11 reflete o crescimento do domínio (novos serviços, DTOs, Swagger). O LCOM 1 → 2 indica que alguns métodos usam apenas parte dos três serviços injetados — candidato a divisão futura.
- **ProcessadorVendaService / SolicitacaoCredito / Repository**: estáveis, sem regressão.

---

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
