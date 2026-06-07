package com.trustlink.trustlink.analyzer;

import com.trustlink.trustlink.model.ApiKey;
import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.IPBlackList;
import com.trustlink.trustlink.model.RateTracker;
import com.trustlink.trustlink.repository.ApiKeyRepository;
import com.trustlink.trustlink.repository.IPBlackListRepository;
import com.trustlink.trustlink.repository.RateTrackerRepository;
import com.trustlink.trustlink.repository.RequestLogRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BehaviorAnalyzer {
    private final IPBlackListRepository ipBlackListRepository;

    private final RateTrackerRepository rateTrackerRepository;

    private final RequestLogRepository requestLogRepository;

    private final ApiKeyRepository apiKeyRepository;

    public int getRateDeduction(String ip){
        Optional<RateTracker> rateTracker = rateTrackerRepository.findByIpAddress(ip);
        if(rateTracker.isPresent()){
            int requestCount = rateTracker.get().getRequestCount();
            if(requestCount <= 20){
                return 0;
            }else if(requestCount > 20 && requestCount <= 50){
                return 15;
            } else if(requestCount > 50 && requestCount <= 100){
                return 25;
            } else {
                return 35;
            }
        }
        return 0;
    }
    public int getBlacklistDeduction(String ip){
        Optional<IPBlackList> blackList = ipBlackListRepository.findByIpAddress(ip);
        if(blackList.isPresent()) {
            return 20;
        }else{
            return 0;
        }
    }
    public int getReputationDeduction(String ip){
        long flagged = requestLogRepository.countByIpAddressAndDecision(ip, Decision.FLAGGED);
        long blocked = requestLogRepository.countByIpAddressAndDecision(ip, Decision.BLOCKED);
        if(blocked >= 3){
            return 20;
        }
        if(blocked == 1 || blocked == 2){
            return 15;
        }
        if(flagged >= 3){
            return 10;
        }
        if(flagged == 1 || flagged == 2){
            return 5;
        }
        return 0;
    }
    public int getProbingDeduction(String ip){
        Optional<RateTracker> rateTracker = rateTrackerRepository.findByIpAddress(ip);
        if(rateTracker.isPresent()){
            RateTracker tracker = rateTracker.get();
            int notFoundCount = tracker.getNotFoundCount();
            if(notFoundCount >= 5){
                return 15;
            }else{
                return 0;
            }
        }
        return 0;
    }
    public int getTimeAnomolyDeduction(String ip){
        return 0;
    }
    public int getApiKeyBonus(HttpServletRequest request){
        if(request.getHeader("X-API-Key") != null){
            String apiKey = request.getHeader("X-API-Key");
            try{
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] apiHash = md.digest(apiKey.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : apiHash) {
                    hexString.append(String.format("%02x", b));
                }
                String hashedKey = hexString.toString();

                Optional<ApiKey> apiKey1 = apiKeyRepository.findByKeyHashAndIsActiveTrue(hashedKey);
                if(apiKey1.isPresent()){
                    return 10;
                }else{
                    return 0;
                }
            }catch(NoSuchAlgorithmException e){
                return 0;
            }
        }
        return 0;
    }
}
