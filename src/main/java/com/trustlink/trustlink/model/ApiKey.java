package com.trustlink.trustlink.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String keyHash;
    private String clientName;
    private LocalDateTime createdAt;
    private Boolean isActive;
}
