# Guia de Configuração do Projeto

Este guia descreve o processo de importação do módulo `validator-core` e a preparação do ambiente Maven para o projeto `fintech`.

## 🚀 Passo a Passo

### 1. Importação do Módulo Core
Para que o projeto principal reconheça o validador, é necessário importá-lo como um módulo:

1. Aceda a **File** > **Project Structure** (ou pressione `Ctrl+Alt+Shift+S`).
2. No menu lateral esquerdo, selecione **Modules**.
3. Clique no ícone de soma (**+**) e escolha **Import Module**.
4. Selecione a pasta do projeto **validator-core** e siga as instruções para confirmar a importação.

### 2. Build do validator-core (Maven)
Agora, é necessário compilar e instalar o core no seu repositório local:

1. Abra o painel lateral do **Maven** (lado direito do IntelliJ).
2. Localize o projeto **validator-core**.
3. Expanda a pasta **Lifecycle**.
4. Execute o comando **clean** (clique duplo).
5. Logo de seguida, execute o comando **install**.
   > *Nota: O comando `install` garante que as alterações no core fiquem disponíveis para outros projetos locais.*

### 3. Limpeza do Projeto Fintech
Por fim, limpe os artefactos antigos do projeto principal:

1. No mesmo painel do **Maven**, localize o projeto **fintech**.
2. Expanda a pasta **Lifecycle**.
3. Execute o comando **clean**.

---

## 🛠️ Resumo de Comandos

| Ordem | Projeto | Ação Maven |
| :--- | :--- | :--- |
| 1 | `validator-core` | `clean` |
| 2 | `validator-core` | `install` |
| 3 | `fintech` | `clean` |

---
**Dica:** Se encontrar erros de dependência após estes passos, utilize o botão **"Reload All Maven Projects"** (ícone de setas circulares no topo do painel Maven).
