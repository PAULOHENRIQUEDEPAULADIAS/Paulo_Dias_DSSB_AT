package com.example.DSSB_AT;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@TestConfiguration
public class TestAuditingConfig  {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return () -> Optional.of("test-user");
    }
}
