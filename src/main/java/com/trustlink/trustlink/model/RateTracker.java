package com.trustlink.trustlink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_tracker")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateTracker {
    @Id
    private String ipAddress;
    private LocalDateTime windowStart;
    private Integer requestCount;
    private Integer notFoundCount;
    private Integer blockedCount;
}
