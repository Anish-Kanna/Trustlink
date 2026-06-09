package com.trustlink.trustlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrustlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrustlinkApplication.class, args);
    }

}
