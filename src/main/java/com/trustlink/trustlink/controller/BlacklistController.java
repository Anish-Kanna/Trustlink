package com.trustlink.trustlink.controller;

import com.trustlink.trustlink.dto.BlacklistRequest;
import com.trustlink.trustlink.model.BlacklistSource;
import com.trustlink.trustlink.model.IPBlackList;
import com.trustlink.trustlink.repository.IPBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final IPBlackListRepository ipBlackListRepository;

    @PostMapping
    public IPBlackList blacklistIp(@RequestBody BlacklistRequest blacklistRequest) {
        IPBlackList blacklistEntry = IPBlackList.builder()
                .ipAddress(blacklistRequest.getIp())
                .reason(blacklistRequest.getReason())
                .source(BlacklistSource.MANUAL)
                .createdAt(LocalDateTime.now())
                .expiresAt(null)
                .build();
        return ipBlackListRepository.save(blacklistEntry);
    }

    @DeleteMapping("/{ip}")
    public ResponseEntity<Void> deleteBlacklistedIp(@PathVariable String ip) {
        Optional<IPBlackList> entries = ipBlackListRepository.findByIpAddress(ip);
        if(entries.isPresent()) {
            ipBlackListRepository.delete(entries.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<IPBlackList> getAllBlacklistedIps() {
        return ipBlackListRepository.findAll();
    }
}
