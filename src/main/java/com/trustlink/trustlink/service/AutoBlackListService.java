package com.trustlink.trustlink.service;

import com.trustlink.trustlink.model.BlacklistSource;
import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.IPBlackList;
import com.trustlink.trustlink.repository.IPBlackListRepository;
import com.trustlink.trustlink.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutoBlackListService {

    private final IPBlackListRepository ipBlackListRepository;

    private final RequestLogRepository requestLogRepository;

    public void checkAndBlackList(String ip) {

        long blockedRequests = requestLogRepository.countByIpAddressAndDecisionAndTimestampAfter(ip, Decision.BLOCKED, LocalDateTime.now().minusMinutes(10));

        Optional<IPBlackList> ipBlackList = ipBlackListRepository.findByIpAddress(ip);

        if(blockedRequests >= 3 && ipBlackList.isEmpty()) {
            IPBlackList newIPBlackList = IPBlackList.builder()
                    .ipAddress(ip)
                    .reason("Auto-blacklisted: " + blockedRequests + " blocks in 10 minutes")
                    .source(BlacklistSource.AUTO)
                    .createdAt(LocalDateTime.now())
                    .expiresAt((LocalDateTime.now().plusHours(24)))
                    .build();
            ipBlackListRepository.save(newIPBlackList);

        }
    }
}
