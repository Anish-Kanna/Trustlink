package com.trustlink.trustlink.repository;

import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestLogRepository extends JpaRepository<RequestLog, UUID> {
    List<RequestLog> findByIpAddress(String ip);
    Long countByIpAddressAndDecision(String ip, Decision d);
}
