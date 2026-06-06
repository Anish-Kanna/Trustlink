package com.trustlink.trustlink.repository;

import com.trustlink.trustlink.model.RateTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateTrackerRepository extends JpaRepository<RateTracker, String> {
    Optional<RateTracker> findByIpAddress(String ip);
}
