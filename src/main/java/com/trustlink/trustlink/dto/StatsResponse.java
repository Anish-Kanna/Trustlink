package com.trustlink.trustlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private long totalRequests;

    private long allowedRequests;
    private long flaggedRequests;
    private long blockedRequests;

    private double allowedPercentage;
    private double flaggedPercentage;
    private double blockedPercentage;

    private double averageTrustScore;

    private long blacklistedIps;

    private LocalDateTime generatedAt;
}
