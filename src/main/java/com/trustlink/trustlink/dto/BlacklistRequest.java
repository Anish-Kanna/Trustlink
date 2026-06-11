package com.trustlink.trustlink.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistRequest {
    private String ip;
    private String reason;
}
