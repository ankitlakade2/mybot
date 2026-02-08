package com.communitybot.model;

import java.util.List;

public record AnalyzeResponse(
    String jobTitle,
    String summary,
    List<String> keySkills,
    String sourceUrl
) {}
