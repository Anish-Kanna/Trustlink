package com.trustlink.trustlink.audit;

import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.RequestLog;
import com.trustlink.trustlink.repository.RequestLogRepository;
import com.trustlink.trustlink.scoring.ScoreResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogger {
    private final RequestLogRepository requestLogRepository;

    private final ObjectMapper objectMapper;

    public void log(HttpServletRequest request, ScoreResult score, Decision decision) {
        String breakdownJson;
        try {
            breakdownJson = objectMapper.writeValueAsString(score.breakdown());
        } catch (Exception e) {
            breakdownJson = "{}";
        }

        RequestLog requestLog = RequestLog.builder()
                .ipAddress(request.getRemoteAddr())
                .endpoint(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .httpMethod(request.getMethod())
                .trustScore(score.score())
                .risklevel(score.riskLevel())
                .decision(decision)
                .scoreBreakdown(breakdownJson)
                .payloadHash(null)
                .userAgent(request.getHeader("User-Agent"))
                .outcome(null)
                .build();
        requestLogRepository.save(requestLog);
    }
}
