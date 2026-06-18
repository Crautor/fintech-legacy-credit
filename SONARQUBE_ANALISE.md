# Análise de Qualidade — SonarQube e MetricsTree

---

## 8.1 Identificação

| Campo | Valor |
|---|---|
| Repositório | https://github.com/Crautor/fintech-legacy-credit |
| Hash do primeiro commit (Rodada 1) | `e9ac940` |
| Último commit (Rodada 2) | `c8401c3` |

> Preencher: Nome completo e RA de todos os integrantes do grupo.

---

## 8.2 Configuração Realizada

### pom.xml — propriedades adicionadas

```xml
<!-- SonarQube -->
<sonar.projectKey>fintech-legacy-credit</sonar.projectKey>
<sonar.projectName>Fintech Legacy Credit ADS</sonar.projectName>
<sonar.host.url>http://localhost:9000</sonar.host.url>
<sonar.java.source>21</sonar.java.source>
<sonar.coverage.jacoco.xmlReportPaths>
    ${project.build.directory}/site/jacoco/jacoco.xml
</sonar.coverage.jacoco.xmlReportPaths>
```

### pom.xml — plugins adicionados

```xml
<!-- SonarScanner -->
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.11.0.3922</version>
</plugin>

<!-- JaCoCo -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution><goals><goal>prepare-agent</goal></goals></execution>
        <execution>
            <id>report</id><phase>test</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
</plugin>
```

### Docker

SonarQube executado via `docker-compose up -d sonarqube` (porta 9000).
Versão: SonarQube Community Build v26.5.0.122743.

---

## 8.3 Rodada 1 — Estado Inicial (commit `e9ac940`)

### f) Tabela CBO e LCOM — Rodada 1

> **CBO** ideal ≤ 5 | **LCOM** ideal = 0

| Classe | CBO | LCOM |
|---|---|---|
| AnaliseCreditoService | 0 | 0 |
| SolicitacaoCreditoController | 2 | 1 |
| ProcessadorVendaService | 0 | 0 |
| SolicitacaoCredito | 0 | 0 |
| SolicitacaoCreditoRepository | 1 | 0 |

### g) Overview SonarQube — Rodada 1

| Métrica | Valor |
|---|---|
| Lines of Code | 323 |
| Quality Gate | **Passed ✅** |
| Security | 0 issues (A) |
| Reliability | 2 issues (C) |
| Maintainability | 36 issues (A) |
| Dívida Técnica | **4h 35min** (275 min) |
| Debt Ratio | 2.8% |
| Security Hotspot | 1 (E) |
| Coverage | 0.0% |
| Duplications | 0.0% |

### h) 5 Code Smells mais críticos — Rodada 1

| # | Severidade | Classe | Problema |
|---|---|---|---|
| 1 | CRITICAL | `AnaliseCreditoService` | Cognitive Complexity do método `analisarSolicitacao` = **39** (máx permitido: 15). God Method com if/else aninhados de 5 níveis. |
| 2 | CRITICAL | `SolicitacaoCreditoController` | Literal `"mensagem"` duplicado 5 vezes — deve ser extraído como constante. |
| 3 | MAJOR | `AnaliseCreditoService` | Uso de `System.out.println` em vez de logger (`log.info/warn`). |
| 4 | MAJOR | `AnaliseCreditoService` | Uso da API `java.util.Date` depreciada (`new Date().getDay()`). |
| 5 | MAJOR | `ProcessadorVendaService` | SQL montado por concatenação de String diretamente no código (potencial SQL Injection + violação de responsabilidade única). |

### i) 3 Classes mais problemáticas — Rodada 1

**1. `AnaliseCreditoService`** — 14 issues, CBO=0, LCOM=0
- Método `analisarSolicitacao` com Cognitive Complexity 39 (God Method)
- Toda a lógica de negócio em um único método de 60+ linhas
- Uso de API depreciada (`Date.getDay()`) e `System.out` em vez de logger
- Mistura responsabilidades: validação, consulta externa, decisão de crédito

**2. `SolicitacaoCreditoController`** — 11 imports, LCOM=1
- Literais duplicados (`"mensagem"`, `"erro"`) sem constantes
- Métodos comentados ocupando >50% do arquivo
- Acoplamento direto com a camada de serviço sem DTOs de entrada

**3. `ProcessadorVendaService`** — 0 campos, lógica inline
- SQL embutido por concatenação (`"INSERT INTO PEDIDOS VALUES (" + cliente + "..."`)
- Lógica de frete, imposto e persistência no mesmo método
- Sem injeção de dependência, impossível de testar isoladamente

---

## 8.4 Rodada 2 — Estado Refatorado (commit `c8401c3`)

### j) Tabela Comparativa CBO e LCOM — v1 vs v2

| Classe | CBO v1 | CBO v2 | LCOM v1 | LCOM v2 |
|---|---|---|---|---|
| AnaliseCreditoService | 0 | 7 | 0 | 1 |
| SolicitacaoCreditoController | 2 | 11 | 1 | 2 |
| ProcessadorVendaService | 0 | 1 | 0 | 0 |
| SolicitacaoCredito | 0 | 0 | 0 | 0 |
| SolicitacaoCreditoRepository | 1 | 1 | 0 | 0 |

### k) Overview SonarQube — Rodada 2

| Métrica | Valor |
|---|---|
| Lines of Code | 1.7k |
| Quality Gate | **Failed ❌** |
| Security | 0 issues (A) |
| Reliability | 0 issues (A) ✅ |
| Maintainability | 148 issues (A) |
| Dívida Técnica | **2 dias e 7 horas** (~23h) |
| Debt Ratio | 2.8% |
| Security Hotspot | 0 (A) ✅ |
| Coverage | 0.0% |
| Duplications | 0.0% |

### l) Activity — Evolução entre as rodadas

| Rodada | Horário | Issues |
|---|---|---|
| Rodada 1 (primeiro commit) | 27/05/2026 21:18 | 37 |
| Rodada 2 (último commit) | 27/05/2026 21:20 | 148 |

O gráfico da aba Activity exibe crescimento de issues de ~37 para ~150, reflexo do crescimento de 5x no volume de código (323 → 1.7k linhas).

### m) Comparação da Dívida Técnica

| | Rodada 1 | Rodada 2 | Variação |
|---|---|---|---|
| Dívida absoluta | 4h 35min (275 min) | 23h (2d 7h) | +18h 25min |
| Lines of Code | 323 | 1.700 | +5,3× |
| Dívida por linha | 0,85 min/LOC | 0,81 min/LOC | **-5% (melhora)** |
| Debt Ratio | 2.8% | 2.8% | estável |

> A dívida absoluta cresceu, mas a **densidade de dívida por linha caiu**, indicando que o código novo foi escrito com qualidade ligeiramente superior ao código original.

---

## 8.5 Reflexão do Grupo

### n) Refatorações mais impactantes nas métricas

**1. Extração do God Method (`AnaliseCreditoService`)**
O método `analisarSolicitacao` com Cognitive Complexity 39 foi quebrado em estratégias (`SolicitacaoStrategy`, `AnalisePF`, `AnalisePJ`) e responsabilidades separadas. Isso eliminou os 2 bugs de Reliability (uso de `Date` depreciado) e reduziu a complexidade do núcleo da aplicação.

**2. Introdução de Injeção de Dependência**
O `AnaliseCreditoService` passou de CBO=0 (sem dependências reais, tudo hardcoded) para CBO=7 (dependências explícitas via construtor). O CBO alto em v2 é sinal de design correto — as dependências foram tornadas visíveis e testáveis.

**3. Padrões Strategy e Factory**
A criação de `CreditoStrategyFactory`, `ValidadorDocumentoFactory`, `PagamentoFactory` e `AnaliseCreditoFactory` substituiu os blocos `if/else` por polimorfismo, reduzindo a Cognitive Complexity e tornando o código extensível sem modificação.

**4. Resolução do Security Hotspot**
O único Security Hotspot (E) da Rodada 1 foi eliminado na Rodada 2 (rating A). A remoção do SQL por concatenação e a introdução do repositório JPA eliminaram o risco de injeção.

### o) Classes ainda problemáticas

**`SolicitacaoCreditoController`** (CBO=11, LCOM=2):
Ainda tem CBO elevado por acoplar diretamente 3 serviços (`AnaliseCreditoService`, `ProcessadorAnaliseCreditoService`, `ProcessadorCreditoCore`) e vários DTOs. O que impede a refatoração completa é a necessidade de manter a API REST estável — quebrar o controller em múltiplos exigiria versionamento de endpoints.

**`FrameworkDemoMain`** (57 code smells):
Classe de demonstração criada para fins didáticos, com muitos `System.out.println`. Por ser código de exemplo e não de produção, a refatoração foi desprioritizada. Em um projeto real, seria excluída ou movida para um módulo separado.

### p) O que faríamos diferente se o projeto começasse hoje?

1. **Testar desde o início**: a cobertura de testes ficou em 0% nas duas rodadas. TDD desde o primeiro commit evitaria bugs como o uso de `Date` depreciado e garantiria que as refatorações não quebrassem comportamentos.

2. **Quality Gate no CI desde o primeiro commit**: configurar o SonarQube para bloquear merges com Cognitive Complexity acima de 15 teria evitado o God Method original.

3. **Separar código de demonstração**: criar um módulo Maven separado para as classes didáticas (`FrameworkDemoMain`, etc.) evitaria que elas poluíssem as métricas do projeto principal.

4. **Commits menores e mais frequentes**: vários commits do histórico agrupam múltiplas refatorações, dificultando isolar o impacto de cada mudança nas métricas.
