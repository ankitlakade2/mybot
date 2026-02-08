package com.communitybot.model;

import jakarta.validation.constraints.NotBlank;

public record AnalyzeRequest(@NotBlank String jobUrl) {}
