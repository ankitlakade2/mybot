package com.communitybot.model;

public record OptimizeRequest(
    String jobUrl,
    String resumeUrl,
    String resumeText
) {}
