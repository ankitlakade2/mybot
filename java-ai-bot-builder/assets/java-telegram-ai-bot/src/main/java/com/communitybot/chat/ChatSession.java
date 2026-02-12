package com.communitybot.chat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ChatSession {
    private final String sessionId;
    private final List<ChatMessage> messages = new ArrayList<>();
    private Instant lastUpdated = Instant.now();

    public ChatSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        lastUpdated = Instant.now();
    }
}
