package com.trustlink.trustlink.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AnalyzeResponse {
    private Integer trustScore;
    private String riskLevel;
    private String decision;
    private Map<String,Integer> scoreBreakdown;
    private List<String> reasons;
    private LocalDateTime timestamp;

}
