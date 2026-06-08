package com.trustlink.trustlink.controller;

import com.trustlink.trustlink.model.RequestLog;
import com.trustlink.trustlink.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final RequestLogRepository requestLogRepository;

    @GetMapping("/")
    public Page<RequestLog> getAllLogs(Pageable pageable) {
        return requestLogRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestLog> getSingleLogById(@PathVariable UUID id) {
        Optional<RequestLog> requestLog = requestLogRepository.findById(id);
        if(requestLog.isPresent()) {
            return ResponseEntity.ok(requestLog.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ip/{ip}")
    public List<RequestLog> getAllLogsOfIp(@PathVariable String ip) {
        return requestLogRepository.findByIpAddress(ip);
    }
}
