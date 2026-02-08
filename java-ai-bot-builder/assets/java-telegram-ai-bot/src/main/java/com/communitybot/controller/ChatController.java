package com.communitybot.controller;

import com.communitybot.chat.ChatRequest;
import com.communitybot.chat.ChatResponse;
import com.communitybot.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public ChatResponse sendMessage(@Valid @RequestBody ChatRequest request) {
        return chatService.reply(request.sessionId(), request.message());
    }
}
