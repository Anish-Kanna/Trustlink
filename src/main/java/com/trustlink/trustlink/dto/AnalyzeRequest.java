package com.trustlink.trustlink.dto;

import java.util.Map;

public class AnalyzeRequest {
    private String payload;
    private String endpoint;
    private String httpMethod;
    private Map<String, String> headers;
}
