package com.example.DSSB_AT.AuditTest;

import com.example.DSSB_AT.Audit.SpringSecurityAuditorAware;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SpringSecurityAuditorAwareTest {

    private SpringSecurityAuditorAware auditorAware;

    @BeforeEach
    void setUp() {
        auditorAware = new SpringSecurityAuditorAware();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveRetornarSystemQuandoNaoHaAutenticacao() {
        SecurityContextHolder.getContext().setAuthentication(null);

        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertThat(auditor).contains("SYSTEM");
    }

    @Test
    void deveRetornarSystemQuandoAutenticacaoAnonima() {
        Authentication anon = new AnonymousAuthenticationToken(
                "key", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
        );
        SecurityContextHolder.getContext().setAuthentication(anon);

        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertThat(auditor).contains("SYSTEM");
    }

    @Test
    void deveRetornarUsernameQuandoPrincipalForUserDetails() {
        UserDetails user = User.withUsername("professor@email.com")
                .password("123456")
                .roles("PROFESSOR")
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertThat(auditor).contains("professor@email.com");
    }

    @Test
    void deveRetornarSYSTEMQuandoPrincipalForString() {
        Authentication auth = new UsernamePasswordAuthenticationToken("usuario123", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertThat(auditor).contains("usuario123");
    }

    @Test
    void deveRetornarEmptyQuandoPrincipalNaoForUserDetailsNemString() {
        Authentication auth = new UsernamePasswordAuthenticationToken(42, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertThat(auditor).isEmpty();
    }
}
