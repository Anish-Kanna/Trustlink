package com.trustlink.trustlink.scoring;

import com.trustlink.trustlink.analyzer.BehaviorAnalyzer;
import com.trustlink.trustlink.analyzer.InputAnalyzer;
import com.trustlink.trustlink.model.RiskLevel;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrustScoreCalculator {
    private final InputAnalyzer I_analyzer;
    private final BehaviorAnalyzer B_Analyzer;


    public ScoreResult trustScoreCalculator(HttpServletRequest request, String body) {
        int score = 100;
        Map<String, Integer> scoreBreakdown = new LinkedHashMap<>();
        String ip = request.getRemoteAddr();
        int sqliDeduction = I_analyzer.getSqliDeduction(body);
        scoreBreakdown.put("sqli_deduction", sqliDeduction);

        int xssDeduction =  I_analyzer.getXssDeduction(body);
        scoreBreakdown.put("XSS_deduction", xssDeduction);

        int headerDeduction = I_analyzer.getHeaderDeduction(request);
        scoreBreakdown.put("Header_deduction", headerDeduction);

        int payloadSizeDeduction = I_analyzer.getPayloadSizeDeduction(body);
        scoreBreakdown.put("PayloadSize_deduction", payloadSizeDeduction);

        int rateDeduction = B_Analyzer.getRateDeduction(ip);
        scoreBreakdown.put("Rate_deduction", rateDeduction);

        int blacklistDeduction = B_Analyzer.getBlacklistDeduction(ip);
        scoreBreakdown.put("BlackList_deduction", blacklistDeduction);

        int reputationDeduction = B_Analyzer.getReputationDeduction(ip);
        scoreBreakdown.put("Reputation_deduction", reputationDeduction);

        int probingDeduction = B_Analyzer.getProbingDeduction(ip);
        scoreBreakdown.put("Probing_deduction", probingDeduction);

        int apiKeyBonus = B_Analyzer.getApiKeyBonus(request);
        scoreBreakdown.put("ApiKeyBonus", apiKeyBonus);

        score = score
                - sqliDeduction
                - xssDeduction
                - headerDeduction
                - payloadSizeDeduction
                - rateDeduction
                - blacklistDeduction
                -  reputationDeduction
                - probingDeduction
                + apiKeyBonus;

        score = Math.max(0, Math.min(100, score));

        //Risk level
        if(score >= 80 && score <= 100){
           return new ScoreResult(score, RiskLevel.TRUSTED, scoreBreakdown);
        }
        if(score >= 45 && score < 80){
            return new ScoreResult(score, RiskLevel.SUSPICIOUS, scoreBreakdown);
        }
        return new ScoreResult(score, RiskLevel.HIGH_RISK, scoreBreakdown);
    }
}
