package com.communitybot.service;

import com.communitybot.chat.ChatMessage;
import com.communitybot.chat.ChatResponse;
import com.communitybot.chat.ChatSession;
import com.communitybot.chat.Role;
import com.communitybot.config.BotSettings;
import com.communitybot.repository.ChatSessionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatSessionRepository sessionRepository;
    private final AiService aiService;
    private final BotSettings settings;

    public ChatService(ChatSessionRepository sessionRepository, AiService aiService, BotSettings settings) {
        this.sessionRepository = sessionRepository;
        this.aiService = aiService;
        this.settings = settings;
    }

    public ChatResponse reply(String sessionId, String userMessage) {
        ChatSession session = sessionRepository.getOrCreate(sessionId);
        session.addMessage(new ChatMessage(Role.USER, userMessage));

        String context = buildContext(session);
        String response = aiService.generate(context);

        session.addMessage(new ChatMessage(Role.ASSISTANT, response));
        trimHistory(session);

        List<ChatMessage> history = session.getMessages().stream().collect(Collectors.toList());
        return new ChatResponse(sessionId, response, history);
    }

    private String buildContext(ChatSession session) {
        StringBuilder builder = new StringBuilder();
        for (ChatMessage message : session.getMessages()) {
            builder.append(message.role().name()).append(": ").append(message.content()).append("\n");
        }
        builder.append("ASSISTANT:");
        return builder.toString();
    }

    private void trimHistory(ChatSession session) {
        int max = settings.maxHistoryMessages();
        List<ChatMessage> messages = session.getMessages();
        if (messages.size() > max) {
            int start = messages.size() - max;
            List<ChatMessage> trimmed = messages.subList(start, messages.size());
            messages.clear();
            messages.addAll(trimmed);
        }
    }
}
