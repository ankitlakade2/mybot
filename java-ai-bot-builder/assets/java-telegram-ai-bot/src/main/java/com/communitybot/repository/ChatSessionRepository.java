package com.communitybot.repository;

import com.communitybot.chat.ChatSession;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ChatSessionRepository {
    private final Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public ChatSession getOrCreate(String sessionId) {
        return sessions.computeIfAbsent(sessionId, ChatSession::new);
    }

    public Optional<ChatSession> findById(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }

    public Collection<ChatSession> findAll() {
        return sessions.values();
    }

    public int purgeOlderThan(Duration maxAge) {
        Instant cutoff = Instant.now().minus(maxAge);
        int removed = 0;
        for (ChatSession session : sessions.values()) {
            if (session.getLastUpdated().isBefore(cutoff)) {
                sessions.remove(session.getSessionId());
                removed++;
            }
        }
        return removed;
    }
}
