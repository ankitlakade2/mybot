package com.communitybot.service;

import com.communitybot.model.OptimizeResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {
    private final UrlFetcher urlFetcher;
    private final AiService aiService;

    public ResumeService(UrlFetcher urlFetcher, AiService aiService) {
        this.urlFetcher = urlFetcher;
        this.aiService = aiService;
    }

    public OptimizeResponse optimize(String jobUrl, String resumeUrl, String resumeText) {
        String jobText = urlFetcher.fetchText(jobUrl);
        String resumeContent = resolveResume(resumeUrl, resumeText);

        String prompt = String.join("\n",
            "You are helping tailor a resume for the job post below.",
            "Rewrite the resume to better match the job while staying truthful.",
            "Return the output in this exact format:",
            "Summary: <2-3 sentence tailored summary>",
            "Changes:",
            "- <bullet 1>",
            "- <bullet 2>",
            "Resume:",
            "<optimized resume text>",
            "Job post:",
            jobText,
            "Resume:",
            resumeContent
        );

        String response = aiService.generate(prompt);
        String summary = extractSection(response, "Summary:");
        List<String> changes = extractBulletSection(response, "Changes:");
        String optimizedResume = extractSection(response, "Resume:");

        return new OptimizeResponse(summary, optimizedResume, changes);
    }

    private String resolveResume(String resumeUrl, String resumeText) {
        if (resumeText != null && !resumeText.isBlank()) {
            return resumeText;
        }
        if (resumeUrl == null || resumeUrl.isBlank()) {
            throw new IllegalArgumentException("resumeUrl or resumeText must be provided");
        }
        return urlFetcher.fetchText(resumeUrl);
    }

    private String extractSection(String response, String marker) {
        String[] lines = response.split("\n");
        boolean inSection = false;
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith(marker)) {
                inSection = true;
                String remainder = line.substring(marker.length()).trim();
                if (!remainder.isBlank()) {
                    builder.append(remainder).append(" ");
                }
                continue;
            }
            if (inSection) {
                if (line.endsWith(":") && !line.startsWith("-")) {
                    break;
                }
                builder.append(line).append("\n");
            }
        }
        return builder.toString().trim();
    }

    private List<String> extractBulletSection(String response, String marker) {
        String[] lines = response.split("\n");
        boolean inSection = false;
        List<String> bullets = new java.util.ArrayList<>();
        for (String line : lines) {
            if (line.startsWith(marker)) {
                inSection = true;
                continue;
            }
            if (inSection) {
                if (line.endsWith(":") && !line.trim().startsWith("-")) {
                    break;
                }
                if (line.trim().startsWith("-")) {
                    String cleaned = line.replaceFirst("-", "").trim();
                    if (!cleaned.isBlank()) {
                        bullets.add(cleaned);
                    }
                }
            }
        }
        return bullets;
    }
}
