# Fintech Legacy Credit - Análise de Crédito com Spring Boot

Aplicação de análise de crédito desenvolvida com Spring Boot 4.0.3, JPA, H2 Database e DevTools.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.0.3**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
- **H2 Database** (Banco de dados em memória para testes e desenvolvimento)
- **Spring Boot DevTools** (Hot reload)
- **Lombok** (Redução de boilerplate)
- **JUnit 5** (Testes)
- **Mockito** (Mock de dependências)

## 📋 Pré-requisitos

- Java 21 instalado
- Maven 3.8+ instalado
- Git (opcional)

## 🔧 Configuração

### 1. Clonar ou extrair o projeto

```bash
cd fintech-legacy-credit
```

### 2. Instalar dependências

```bash
mvn clean install
```

### 3. Executar a aplicação

#### Opção 1: Via Maven
```bash
mvn spring-boot:run
```

#### Opção 2: Via IDE (IntelliJ IDEA)
1. Clique com botão direito em `Main.java`
2. Selecione "Run 'Main'"

#### Opção 3: Compilar e executar JAR
```bash
mvn clean package
java -jar target/fintech-legacy-credit-1.0-SNAPSHOT.jar
```

## 🌐 Acessar a Aplicação

A aplicação estará disponível em: **http://localhost:8080**

### Console H2 Database
Acesse o console do banco de dados em: **http://localhost:8080/api/h2-console**
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: (deixe em branco)

## 📚 Endpoints da API

### 1. Analisar Solicitação de Crédito
```http
POST http://localhost:8080/api/solicitacoes/analisar
```

**Parâmetros:**
- `cliente` (String): Nome do cliente
- `documento` (String): CPF (PF) ou CNPJ (PJ)
- `valor` (Double): Valor solicitado
- `score` (Integer): Score de crédito (0-1000)
- `negativado` (Boolean, opcional): Cliente negativado? (padrão: false)
- `tipoConta` (String, opcional): PF ou PJ (padrão: PF)
- `pais` (String, opcional): BR, US ou MX (padrão: BR)

**Exemplo:**
```bash
curl -X POST "http://localhost:8080/api/solicitacoes/analisar?cliente=Jose%20Silva&documento=123.456.789-09&valor=5000&score=750&negativado=false&tipoConta=PF"
```

**Resposta:**
```json
{
  "cliente": "Jose Silva",
  "documento": "123.456.789-09",
  "valor": 5000.0,
  "aprovado": true,
  "mensagem": "Solicitação aprovada"
}
```

### 2. Processar Lote de Solicitações
```http
POST http://localhost:8080/api/solicitacoes/processar-lote
Content-Type: application/json
```

**Exemplo:**
```bash
curl -X POST "http://localhost:8080/api/solicitacoes/processar-lote" \
  -H "Content-Type: application/json" \
  -d '["Cliente1", "Cliente2", "Cliente3"]'
```

**Resposta:**
```json
{
  "mensagem": "Lote processado com sucesso",
  "totalClientes": "3"
}
```

### 3. Health Check
```http
GET http://localhost:8080/api/solicitacoes/saude
```

**Exemplo:**
```bash
curl "http://localhost:8080/api/solicitacoes/saude"
```

**Resposta:**
```json
{
  "status": "ok",
  "mensagem": "Aplicação funcionando corretamente"
}
```

## 🧪 Executar Testes

### Testes Unitários
```bash
mvn test
```

### Testes de Integração
```bash
mvn verify
```

### Testes com Coverage
```bash
mvn clean test jacoco:report
```

## 📊 Estrutura do Projeto

```
fintech-legacy-credit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/nogueiranogueira/aularefatoracao/
│   │   │       ├── Main.java (Spring Boot Application)
│   │   │       ├── controller/
│   │   │       │   └── SolicitacaoCreditoController.java
│   │   │       ├── service/
│   │   │       │   └── AnaliseCreditoService.java
│   │   │       ├── repository/
│   │   │       │   └── SolicitacaoCreditoRepository.java
│   │   │       └── model/
│   │   │           └── SolicitacaoCredito.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── br.com.nogueiranogueira.aularefatoracao.TestAnaliseCreditoService.java
│           └── SolicitacaoCreditoIntegrationTest.java
└── pom.xml
```

## 🔐 Regras de Negócio

### Critérios de Aprovação

**Pessoa Física (PF):**
- Score mínimo: 500
- Não pode estar negativado
- Se valor > R$ 5.000, score deve ser > 800
- Não aprovado em finais de semana (requer aprovação manual)

**Pessoa Jurídica (PJ):**
- Score mínimo: 500
- Não pode estar negativado
- Se valor > R$ 50.000, score deve ser > 700

## 📝 Logs

Os logs são configurados em diferentes níveis:
- **INFO**: Informações gerais da aplicação
- **DEBUG**: Informações detalhadas para debug
- **WARN**: Avisos e solicitações reprovadas
- **ERROR**: Erros de processamento

Veja `application.properties` para configurar os níveis de log.

## 🆘 Troubleshooting

### Porta 8080 em uso
```bash
# Mude a porta no application.properties
server.port=8081
```

### Erro de dependências Maven
```bash
mvn clean install -U
```

### Limpar cache do Maven
```bash
mvn clean install
```

## 📄 Licença

Este projeto é parte de um exercício de refatoração de código legado.

## 👨‍💻 Autor

Desenvolvido como exemplo de aplicação Spring Boot com boas práticas de desenvolvimento.

---

## 🏛️ Defesa Arquitetural

### 1. A Diferença Prática: Biblioteca vs Framework

Ao construir o `validator-core` como **biblioteca**, a equipe percebeu que ela é passiva: entrega classes e métodos, mas quem decide quando e como chamar é sempre o consumidor. Não há fluxo imposto. O código-cliente está no controle.

Ao construir o `fintech-legacy-credit` como **framework**, a relação se inverte: o framework define o fluxo e "liga de volta" para o código do cliente no momento que escolhe. O cliente não chama o framework — é o framework que chama o cliente. Isso é o **Princípio de Hollywood** ("Don't call us, we'll call you").

Na prática: na biblioteca você escreve um método e chama quando quiser. No framework você sobrescreve um método (hook) ou implementa uma interface e espera o framework invocá-la.

---

### 2. Acoplamento: Por que Black-box é preferível à White-box?

**White-box (Herança):**
```java
// O cliente PRECISA conhecer os detalhes internos para estender
public class ValidadorTransacaoPF extends TransacaoValidadorTemplate {
    @Override
    protected boolean validarDocumento(Transacao t) { ... }
    @Override
    protected boolean validarValor(Transacao t) { ... }
    @Override
    protected boolean validarRegrasEspecificas(Transacao t) { ... }
}
```
- Java permite **herança simples** — `ValidadorTransacaoPF` não pode estender mais nada.
- Uma mudança na superclasse (ex: adicionar um passo ao `validar()`) pode quebrar **todas** as subclasses.
- Regras não podem ser combinadas livremente: para misturar PF com uma regra extra é preciso criar nova subclasse.

**Black-box (Composição/Interfaces):**
```java
// O cliente implementa interfaces leves e injeta no engine
TransacaoValidadorEngine engine = new TransacaoValidadorEngine(List.of(
    new RegraDocumento(),
    new RegraValorMaximo(new BigDecimal("50000.00")),
    new RegraScoreMinimo(300)
));
```
- Cada regra é **independente** — pode ser testada isoladamente com um `Transacao` de teste.
- Adicionar ou remover uma regra não toca no código existente (**Open/Closed Principle**).
- O engine não sabe (e não precisa saber) o que cada `RegraValidacao` faz internamente.

O ecossistema Spring moderno segue exatamente essa lógica: você anota `@Service`, `@Repository` e injeta interfaces — nunca estende classes do Spring. O Spring é o engine; você fornece as implementações.

---

### 3. Inversão de Controle — onde ela acontece

#### White-box (Template Method)

```java
// TransacaoValidadorTemplate.java — linha 28
public final ResultadoValidacao validar(Transacao transacao) {

    // ← IoC aqui: o framework chama o método implementado pelo cliente
    if (!validarDocumento(transacao)) {
        return ResultadoValidacao.falha("Documento inválido para tipo " + transacao.tipo());
    }
    // ← IoC aqui: o framework chama o método implementado pelo cliente
    if (!validarValor(transacao)) {
        return ResultadoValidacao.falha("Valor fora dos limites permitidos");
    }
    // ← IoC aqui: o framework chama o método implementado pelo cliente
    if (!validarRegrasEspecificas(transacao)) {
        return ResultadoValidacao.falha("Regra de negócio específica violada");
    }
    return ResultadoValidacao.sucesso();
}
```

O método `validar()` é `final` — o cliente **não pode** alterar a ordem. O framework decide quando cada hook é invocado.

#### Black-box (Strategy + Injeção de Dependência)

```java
// TransacaoValidadorEngine.java — linha 52
for (RegraValidacao regra : regras) {

    // ← IoC aqui: o engine decide quando chamar cada regra do cliente
    ResultadoValidacao resultado = regra.validar(transacao);

    if (!resultado.aprovado()) return resultado;
}
```

O engine itera sobre as regras injetadas e chama `validar()` em cada uma. O cliente forneceu as implementações (`RegraDocumento`, `RegraScoreMinimo`, `RegraValorMaximo`), mas nunca as chamou diretamente — quem as invoca é o engine.

Em ambos os casos, **o fluxo de controle pertence ao framework**, não ao cliente.

