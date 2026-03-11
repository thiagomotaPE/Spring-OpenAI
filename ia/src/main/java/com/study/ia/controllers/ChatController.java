package com.study.ia.controllers;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/informations")
    public String chat(@RequestParam(value = "message", defaultValue = "Me diga os 5 livros mais vendidos sobre economia em 2025") String message) {
        return chatModel.call(message);
    }

//    @GetMapping("/informations")
//    public ChatResponse chat(@RequestParam(value = "message", defaultValue = "Me diga os 5 livros mais vendidos sobre economia em 2025") String message) {
//        return chatModel.call(new Prompt(message));
//    }

    @GetMapping("/reviews")
    public String bookstoreReview(@RequestParam(value = "book", defaultValue = "Dom Quixote") String book) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                      Por favor, me forneça
                      um breve resumo do livro {book}
                      e também a biografia de seu autor.
                    """);
        promptTemplate.add("book", book);
        return this.chatModel.call(promptTemplate.create()).getResult().getOutput().getText();
    }

    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
        return chatModel.stream(message);
    }

//    @GetMapping("/stream/informations")
//    public Flux<ChatResponse> bookstoreChatStreamEx2(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatModel.stream(new Prompt(message));
//    }

}
