package com.trustlink.trustlink.scoring;

import com.trustlink.trustlink.model.RiskLevel;

import java.util.Map;

public record ScoreResult(int score, RiskLevel riskLevel, Map<String, Integer> breakdown){}
