# Análise de Dívidas Técnicas e Complexidade - Serviços

Este documento apresenta a análise detalhada das dívidas técnicas e da complexidade sintomática encontradas nas classes de serviço do projeto **Fintech Legacy Credit**.

## 1. `AnaliseCreditoService.java`

Esta classe contém a regra de negócio para a aprovação de crédito e apresenta uma série de problemas de *design* e código legado.

### Complexidade Sintomática (*Arrow Anti-Pattern*)
* **Aninhamento Profundo de `if/else`**: O método `analisarSolicitacao` possui pelo menos 5 níveis de aninhamento de estruturas condicionais (ex: verificação de valor > 0, seguido de negativação, seguido de *score*, seguido de tipo de conta). Isso torna a leitura muito difícil e fere a legibilidade do código.
* **Falta de *Early Return* (*Fail-Fast*)**: Em vez de validar e retornar `false` imediatamente nas pré-condições, a lógica de sucesso fica encapsulada no bloco mais profundo da estrutura.

### Dívidas Técnicas Identificadas
* **Uso de API Depreciada**: O código utiliza `new Date().getDay()` para verificar se é fim de semana. Este método está depreciado no Java; o correto seria adotar a moderna API `java.time` (ex: `LocalDate` ou `LocalDateTime`).
* **Números e *Strings* Mágicas (*Magic Numbers/Strings*)**: Valores soltos no código como `5000`, `800`, `50000`, `700`, `"PF"`, `"PJ"` não possuem definição como constantes ou enumerações, o que dificulta a manutenção e abre margem para erros.
* **Bloqueio da *Thread* (`Thread.sleep`)**: A simulação de consulta a um *bureau* de crédito faz uso de `Thread.sleep(2000)` diretamente na *thread* de execução do serviço. Num cenário de alto volume de acessos, isso travaria a aplicação.
* **Logs Inadequados**: Utilização massiva de `System.out.println` no lugar de um *framework* de *logging* profissional (como o SLF4J, que já está disponível no projeto através do Lombok).
* **Múltiplos Parâmetros Primitivos (*Primitive Obsession*)**: O método recebe cinco parâmetros soltos em vez de receber a própria entidade ou um DTO de contexto, como um objeto `SolicitacaoCredito`.

---

## 2. `ProcessadorVendaService.java`

Esta classe simula o processamento de uma venda com cálculos de frete e impostos.

### Complexidade Sintomática
* **Cadeias de `if-else` para Regras de Negócio**: O cálculo do frete utiliza uma sequência de `if / else if / else` para avaliar o prefixo do código postal/CEP (`"85"`, `"01"`). O mesmo ocorre para o cálculo de imposto com base no tipo (`"PRODUTO"`, `"SERVICO"`). Essa abordagem fere o princípio OCP (*Open-Closed Principle*), exigindo a modificação direta do método a cada nova regra de frete ou tipo de venda.

### Dívidas Técnicas Identificadas
* **Vulnerabilidade de Injeção SQL e Má Prática de Arquitetura**: O serviço faz uma simulação de persistência utilizando concatenação direta de *Strings* num comando SQL: `"INSERT INTO PEDIDOS VALUES (" + cliente + ... )`. Além de ser uma grave falha de segurança (*SQL Injection*), a camada de serviço não deveria ter responsabilidades diretas sobre *queries* de base de dados (evidenciando a ausência do padrão *Repository*).
* **Violação do SRP (*Single Responsibility Principle*)**: O método `processar` engloba validação de parâmetros, lógica de tarifação (frete), cálculo tributário (impostos) e persistência de dados. Ele possui demasiadas razões para mudar, ferindo o princípio da responsabilidade única.
* **Uso de `System.out.println`**: À semelhança do que acontece no serviço de crédito, não há a adoção de uma estrutura de *logs* formal e rastreável.
* **Valores *Hardcoded***: As taxas de imposto (`0.18`, `0.05`) e os valores de frete (`10.0`, `20.0`, `50.0`) estão fixados diretamente no meio da lógica, sem documentação, explicação ou uso de constantes.