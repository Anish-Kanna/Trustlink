package com.trustlink.trustlink.controller;

import com.trustlink.trustlink.dto.StatsResponse;
import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.IPBlackList;
import com.trustlink.trustlink.model.RequestLog;
import com.trustlink.trustlink.repository.IPBlackListRepository;
import com.trustlink.trustlink.repository.RequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/stats")
public class StatsController {

    private final RequestLogRepository requestLogRepository;

    private final IPBlackListRepository ipBlackListRepository;

    @GetMapping("/summary")
    public StatsResponse getSummary() {
        long totalRequests = requestLogRepository.count();

        long allowedRequests = requestLogRepository.countByDecision(Decision.ALLOWED);

        long flaggedRequests = requestLogRepository.countByDecision(Decision.FLAGGED);

        long blockedRequests = requestLogRepository.countByDecision(Decision.BLOCKED);

        double allowedPct = totalRequests > 0 ? (allowedRequests * 100.0 / totalRequests) : 0;

        double flaggedPct = totalRequests > 0 ? (flaggedRequests * 100.0 / totalRequests) : 0;

        double blockedPct = totalRequests > 0 ? (blockedRequests * 100.0 / totalRequests) : 0;

        List<RequestLog> allLogs = requestLogRepository.findAll();

        double avgTrustScore = allLogs.stream()
                .filter(log -> log.getTrustScore() != null)
                .mapToInt(RequestLog::getTrustScore)
                .average()
                .orElse(0.0);

        long blackListedIps = ipBlackListRepository.count();

        return StatsResponse.builder()
                .totalRequests(totalRequests)
                .allowedRequests(allowedRequests)
                .flaggedRequests(flaggedRequests)
                .blockedRequests(blockedRequests)
                .allowedPercentage(allowedPct)
                .flaggedPercentage(flaggedPct)
                .blockedPercentage(blockedPct)
                .averageTrustScore(avgTrustScore)
                .blacklistedIps(blackListedIps)
                .generatedAt(LocalDateTime.now())
                .build();

    }

    @GetMapping("/risky-ips")
    public List<IPBlackList> getRiskyIps() {
        return ipBlackListRepository.findAll();
    }
}
