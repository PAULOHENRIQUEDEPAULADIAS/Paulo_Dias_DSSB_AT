package com.example.DSSB_AT.Security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void deveCarregarBeansPrincipaisDeSecurity() throws Exception {
        SecurityFilterChain filterChain = context.getBean(SecurityFilterChain.class);
        assertThat(filterChain).isNotNull();

        AuthenticationManager authManager = context.getBean(AuthenticationManager.class);
        assertThat(authManager).isNotNull();

        AuthenticationProvider authProvider = context.getBean(AuthenticationProvider.class);
        assertThat(authProvider).isNotNull();

        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder).isInstanceOf(PasswordEncoder.class);
    }
}
