package com.trustlink.trustlink.repository;

import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.IPBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPBlackListRepository extends JpaRepository<IPBlackList, UUID> {
    Optional<IPBlackList> findByIpAddress(String ip);
    List<IPBlackList> findByExpiresAtBefore(LocalDateTime now);
    void deleteByExpiresAtBefore(LocalDateTime now);

}
