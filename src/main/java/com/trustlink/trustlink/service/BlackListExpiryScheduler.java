package com.trustlink.trustlink.service;

import com.trustlink.trustlink.repository.IPBlackListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BlackListExpiryScheduler {

    private final IPBlackListRepository ipBlackListRepository;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void cleanExpiredBlacklists() {
        ipBlackListRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
