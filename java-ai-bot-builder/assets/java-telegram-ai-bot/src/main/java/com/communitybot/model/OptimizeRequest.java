package com.communitybot.model;

import jakarta.validation.constraints.NotBlank;

public record OptimizeRequest(
    @NotBlank String jobUrl,
    String resumeUrl,
    String resumeText
) {}
