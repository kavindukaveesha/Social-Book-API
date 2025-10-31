package com.NextCoreInv.book_network.role;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@TestConfiguration
public class TestJpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("1");
    }
}
