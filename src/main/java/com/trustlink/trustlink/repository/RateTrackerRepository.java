package com.trustlink.trustlink.repository;

import com.trustlink.trustlink.model.RateTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateTrackerRepository extends JpaRepository<RateTracker, String> {
}
