package com.example.demo.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Value("${security.secret:oursecretkey}")
    private String secret;
    @Value("${security.expiration.time:864000000}") // 10 days
    private long expirationTime;

    @AllArgsConstructor
    @Getter
    public static final class SecurityProps {
        private final String secret;
        private final long expirationTime;
    }

    @Bean
    public SecurityProps getSecurityProps() {
        return new SecurityProps(secret, expirationTime);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
