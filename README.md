# BarberFlow API 💈

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

API RESTful para o projeto **BarberFlow**, um sistema de gerenciamento de agendamentos para barbearias. Este projeto foi desenvolvido do zero com o objetivo de criar uma solução completa, desde a lógica de negócio até a integração com serviços de notificação.

---

## ✨ Funcionalidades (Versão Atual)

- **Criação de Agendamentos:** Endpoint para criar novos agendamentos, com validação de conflito de horários para o mesmo barbeiro.
- **Listagem de Agendamentos:** Endpoint para consultar a agenda de um dia específico.
- **Notificação via WhatsApp:** Integração com a API da Twilio para enviar uma mensagem de confirmação ao cliente no momento em que o agendamento é criado com sucesso.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:**
    - Java 21
    - Spring Boot 3
    - Spring Web (para a criação da API REST)
    - Spring Data JPA (para persistência de dados)
    - Hibernate
- **Banco de Dados:**
    - H2 Database (para ambiente de desenvolvimento)
    - PostgreSQL (planejado para produção)
- **Comunicação:**
    - Twilio API para envio de mensagens via WhatsApp.
- **Ferramentas e Outros:**
    - Maven (Gerenciador de dependências)
    - Lombok (Redução de código boilerplate)

---

## ⚙️ Configuração do Ambiente

Para executar este projeto, você precisa configurar suas credenciais da API da Twilio.

1.  **Crie o arquivo de configuração:** Na pasta `src/main/resources`, crie um arquivo chamado `application.properties`.

2.  **Adicione as configurações:** Cole o conteúdo abaixo no arquivo e substitua os placeholders (`AC...` e `seu_auth_token_aqui`) pelas suas credenciais reais da Twilio.

    ```properties
    # Configuração do Hibernate para criar/atualizar o banco a cada execução
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.defer-datasource-initialization=true

    # Credenciais da API da Twilio
    twilio.account.sid=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    twilio.auth.token=seu_auth_token_aqui
    twilio.whatsapp.number.from=whatsapp:+14155238886
    ```

3.  **IMPORTANTE (Segurança):** Nunca envie suas senhas ou chaves de API para o GitHub. Crie um arquivo na raiz do projeto chamado `.gitignore` e adicione a seguinte linha para garantir que o arquivo de propriedades com suas senhas não seja enviado:
    ```
    application.properties
    ```

---

## ▶️ Como Executar o Projeto

1.  Clone este repositório.
2.  Configure o `application.properties` conforme as instruções acima.
3.  Abra o projeto em sua IDE (como o IntelliJ IDEA).
4.  Execute a classe principal `BarberflowApiApplication.java`.
5.  O servidor estará rodando em `http://localhost:8080`.

---

## Endpoints da API

### 1. Criar um Novo Agendamento

Cria um novo agendamento e dispara uma notificação via WhatsApp.

- **URL:** `/agendamentos`
- **Método:** `POST`
- **Corpo da Requisição (Body):**

  ```json
  {
    "clienteId": 1,
    "barbeiroId": 1,
    "servicoId": 1,
    "dataHora": "2025-10-31T14:00:00"
  }