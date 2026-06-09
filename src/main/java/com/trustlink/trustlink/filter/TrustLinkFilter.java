package com.trustlink.trustlink.filter;

import com.trustlink.trustlink.audit.AuditLogger;
import com.trustlink.trustlink.decision.DecisionEngine;
import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.scoring.ScoreResult;
import com.trustlink.trustlink.scoring.TrustScoreCalculator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TrustLinkFilter extends OncePerRequestFilter {

    private final TrustScoreCalculator trustScoreCalculator;

    private final DecisionEngine decisionEngine;

    private final AuditLogger auditLogger;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/blacklist");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        byte[] bodyBytes = request.getInputStream().readAllBytes();
        String body = new String(bodyBytes);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, 0);
        System.out.println("BODY: " + body);

        ScoreResult result = trustScoreCalculator.trustScoreCalculator(requestWrapper, body);
        request.setAttribute("scoreResult", result);

        Decision decision = decisionEngine.decide(result.score());

        auditLogger.log(requestWrapper, result, decision);

        if(decision == Decision.BLOCKED){
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": \"BLOCKED\", \"trustScore\": " + result.score() + ",\"riskLevel\": \"" + result.riskLevel() + "\"}");
            return;
        }else if(decision == Decision.FLAGGED){
            response.addHeader("X-TrustLink-Warning", "SUSPICIOUS");
            response.addHeader("X-TrustLink-Score", String.valueOf(result.score()));
        }
        filterChain.doFilter(requestWrapper, response);
    }
}
