package com.trustlink.trustlink.controller;

import com.trustlink.trustlink.filter.TrustLinkFilter;
import com.trustlink.trustlink.scoring.ScoreResult;
import com.trustlink.trustlink.scoring.TrustScoreCalculator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzeController {

    @PostMapping("analyze")
    public ScoreResult analyze(HttpServletRequest request) {
        return (ScoreResult) request.getAttribute("scoreResult");
    }
}
