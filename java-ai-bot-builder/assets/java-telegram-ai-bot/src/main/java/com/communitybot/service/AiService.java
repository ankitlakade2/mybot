package com.communitybot.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final ChatLanguageModel model;
    private final String systemPrompt;

    public AiService() {
        String apiKey = requireEnv("OPENAI_API_KEY");
        String modelName = envOrDefault("OPENAI_MODEL", OpenAiChatModelName.GPT_3_5_TURBO);
        this.systemPrompt = envOrDefault("BOT_SYSTEM_PROMPT", DefaultPrompts.RESUME_COACH_PROMPT);

        this.model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName(modelName)
            .temperature(0.3)
            .build();
    }

    public String generate(String taskPrompt) {
        String prompt = systemPrompt + "\n\n" + taskPrompt;
        return model.generate(prompt).content().text();
    }

    public String generateFromHistory(String historyPrompt) {
        return model.generate(systemPrompt + "\n\n" + historyPrompt).content().text();
    }

    private static String requireEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    private static String envOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
