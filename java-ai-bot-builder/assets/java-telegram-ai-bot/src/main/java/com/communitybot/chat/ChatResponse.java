package com.communitybot.chat;

import java.util.List;

public record ChatResponse(
    String sessionId,
    String reply,
    List<ChatMessage> history
) {}
