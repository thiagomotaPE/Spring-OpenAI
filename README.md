# 🤖 Spring AI + OpenAI Chatbot

Aplicação de estudo desenvolvida para explorar a integração do **Spring AI** com a **OpenAI API**, permitindo enviar perguntas e receber respostas geradas por IA.

---

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 4.x**
- **Spring AI**
- **OpenAI API** (GPT)
- **Maven**

---

## 💡 Sobre o projeto

Este projeto foi criado com fins educacionais para explorar como o **Spring AI** abstrai e simplifica a integração com modelos de linguagem da OpenAI. A aplicação expõe um endpoint REST que recebe uma pergunta e retorna a resposta gerada pelo modelo.

---

## 🚀 Como executar

### Pré-requisitos

- Java 21+
- Maven
- Chave de API da OpenAI → [platform.openai.com/api-keys](https://platform.openai.com/api-keys)

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Configure a API Key
Adicione diretamente no `application.properties` (não recomendado para produção):

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## 📡 Endpoints

### `GET http://localhost:8080/chat/informations?message="escreva aqui sua mensagem"`

Envia uma mensagem e recebe uma resposta da IA.

---

## 📚 Referências

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [OpenAI API Reference](https://platform.openai.com/docs/api-reference)
- [Michelli Brito](https://www.youtube.com/watch?v=NscHAlj-yQ0&t=576s)