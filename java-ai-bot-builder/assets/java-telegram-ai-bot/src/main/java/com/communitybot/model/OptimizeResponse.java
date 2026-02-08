package com.communitybot.model;

import java.util.List;

public record OptimizeResponse(
    String tailoredSummary,
    String optimizedResume,
    List<String> changeNotes
) {}
