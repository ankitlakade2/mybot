package com.communitybot.controller;

import com.communitybot.model.AnalyzeRequest;
import com.communitybot.model.AnalyzeResponse;
import com.communitybot.model.OptimizeRequest;
import com.communitybot.model.OptimizeResponse;
import com.communitybot.service.JobPostService;
import com.communitybot.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResumeController {
    private final JobPostService jobPostService;
    private final ResumeService resumeService;

    public ResumeController(JobPostService jobPostService, ResumeService resumeService) {
        this.jobPostService = jobPostService;
        this.resumeService = resumeService;
    }

    @PostMapping("/job/analyze")
    public AnalyzeResponse analyzeJob(@Valid @RequestBody AnalyzeRequest request) {
        if (request == null || request.jobUrl() == null || request.jobUrl().isBlank()) {
            throw new IllegalArgumentException("jobUrl is required");
        }
        return jobPostService.analyze(request.jobUrl());
    }

    @PostMapping("/resume/optimize")
    public OptimizeResponse optimizeResume(@Valid @RequestBody OptimizeRequest request) {
        if (request == null || request.jobUrl() == null || request.jobUrl().isBlank()) {
            throw new IllegalArgumentException("jobUrl is required");
        }
        return resumeService.optimize(request.jobUrl(), request.resumeUrl(), request.resumeText());
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
