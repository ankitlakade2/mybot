package com.communitybot.scheduler;

import com.communitybot.config.BotSettings;
import com.communitybot.repository.ChatSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupTask {
    private final ChatSessionRepository repository;
    private final BotSettings settings;

    public SessionCleanupTask(ChatSessionRepository repository, BotSettings settings) {
        this.repository = repository;
        this.settings = settings;
    }

    @Scheduled(fixedDelayString = "${bot.session-cleanup-ms:300000}")
    public void cleanup() {
        repository.purgeOlderThan(settings.sessionTtl());
    }
}
