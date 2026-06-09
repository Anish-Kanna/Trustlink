package com.trustlink.trustlink.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "ip_blacklist")
@Setter
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class IPBlackList {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String ipAddress;
    private String reason;
    @Enumerated(EnumType.STRING)
    private BlacklistSource source;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
