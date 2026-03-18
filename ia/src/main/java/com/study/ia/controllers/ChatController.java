package com.study.ia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@Tag(name= "chatbot", description = "Endpoints para comunicação com a IA via Spring AI + OpenAI")
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/informations")
    @Operation(
            summary = "Envia uma mensagem para a IA e retorna uma resposta",
            description = "Faz uma chamada direta ao modelo OpenAI e retorna a resposta como texto puro.",
            operationId = "chat"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Resposta gerada com sucesso",
            content = @Content(
                mediaType = MediaType.TEXT_PLAIN_VALUE,
                schema = @Schema(type = "string", example = "1. Pai Rico Pai Pobre...")
            )
    )
    public String chat(
            @Parameter(name = "message", description = "Pergunta ou instrução enviada ao modelo", example = "Me diga os 5 livros mais vendidos sobre economia em 2025", required = false)
            @RequestParam(value = "message", defaultValue = "Me diga os 5 livros mais vendidos sobre economia em 2025") String message) {
        return chatModel.call(message);
    }

//    @GetMapping("/informations")
//    public ChatResponse chat(@RequestParam(value = "message", defaultValue = "Me diga os 5 livros mais vendidos sobre economia em 2025") String message) {
//        return chatModel.call(new Prompt(message));
//    }

    @GetMapping("/reviews")
    @Operation(
            summary = "Gera resumo e biografia do autor de um livro",
            description = """
            Usa um `PromptTemplate` para estruturar a pergunta.
            O modelo retorna um resumo do livro informado e a biografia do seu autor.
            """,
            operationId = "bookstoreReview"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Resumo e biografia gerados com sucesso",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(type = "string",
                                    example = "Dom Quixote foi escrito por Miguel de Cervantes...")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro ao processar o template ou chamar o modelo")
    })
    public String bookstoreReview(
            @Parameter(name = "book", description = "Título do livro que deseja obter o resumo e a biografia do autor", example = "Dom Quixote", required = false)
            @RequestParam(value = "book", defaultValue = "Dom Quixote") String book) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                      Por favor, me forneça
                      um breve resumo do livro {book}
                      e também a biografia de seu autor.
                    """);
        promptTemplate.add("book", book);
        return this.chatModel.call(promptTemplate.create()).getResult().getOutput().getText();
    }

    @GetMapping("/stream/informations")
    @Operation(
            summary = "Retorna resposta da IA em streaming (token a token)",
            description = """
            Utiliza `Flux<String>` para transmitir a resposta conforme cada token
            é gerado pelo modelo. Ideal para interfaces que exibem a resposta em tempo real.
            O `Content-Type` retornado é `text/event-stream`.
            """,
            operationId = "bookstoreChatStream"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Stream iniciado com sucesso",
                    content = @Content(
                            mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
                            schema = @Schema(type = "string",
                                    example = "Os livros best sellers dos últimos anos são...")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro ao iniciar o stream com o modelo")
    })
    public Flux<String> bookstoreChatStream(
            @Parameter(name = "message", description = "Pergunta enviada ao modelo em modo streaming", example = "Quais são os livros best sellers dos últimos anos?", required = false)
            @RequestParam(value = "message", defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
        return chatModel.stream(message);
    }

//    @GetMapping("/stream/informations")
//    public Flux<ChatResponse> bookstoreChatStreamEx2(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatModel.stream(new Prompt(message));
//    }

}
