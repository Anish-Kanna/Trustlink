package com.trustlink.trustlink.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.expression.JsonTableColumnDefinition;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "request_logs")
@Setter
@Getter @NoArgsConstructor @AllArgsConstructor @Builder

public class RequestLog {
    @Id  @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String ipAddress;
    private String endpoint;
    private LocalDateTime timestamp;
    private String httpMethod;
    private Integer trustScore;
    @Enumerated(EnumType.STRING)
    private RiskLevel risklevel;
    @Enumerated(EnumType.STRING)
    private Decision decision;
    @Column (columnDefinition = "jsonb")
    private String scoreBreakdown;
    private String payloadHash;
    private String userAgent;
    private String outcome;
}
