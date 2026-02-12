package com.communitybot.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "bot")
public record BotSettings(
    @DefaultValue("20") int maxHistoryMessages,
    @DefaultValue("PT2H") Duration sessionTtl
) {}
