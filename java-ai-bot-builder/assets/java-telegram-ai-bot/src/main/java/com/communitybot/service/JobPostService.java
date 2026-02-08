package com.communitybot.service;

import com.communitybot.model.AnalyzeResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class JobPostService {
    private final UrlFetcher urlFetcher;
    private final AiService aiService;

    public JobPostService(UrlFetcher urlFetcher, AiService aiService) {
        this.urlFetcher = urlFetcher;
        this.aiService = aiService;
    }

    public AnalyzeResponse analyze(String jobUrl) {
        String jobText = urlFetcher.fetchText(jobUrl);
        String prompt = String.join("\n",
            "Analyze the following job post text.",
            "Return a concise title, a 2-3 sentence summary, and 6-10 key skills.",
            "Format exactly:",
            "Title: <title>",
            "Summary: <summary>",
            "Skills: <comma-separated skills>",
            "Job post:",
            jobText
        );

        String response = aiService.generate(prompt);
        String title = extractLine(response, "Title:");
        String summary = extractLine(response, "Summary:");
        String skillsLine = extractLine(response, "Skills:");
        List<String> skills = Arrays.stream(skillsLine.split(","))
            .map(String::trim)
            .filter(skill -> !skill.isBlank())
            .collect(Collectors.toList());

        return new AnalyzeResponse(title, summary, skills, jobUrl);
    }

    private String extractLine(String response, String prefix) {
        return Arrays.stream(response.split("\n"))
            .filter(line -> line.startsWith(prefix))
            .map(line -> line.substring(prefix.length()).trim())
            .findFirst()
            .orElse("");
    }
}
