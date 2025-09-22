package com.example.DSSB_AT.Security;

import com.example.DSSB_AT.Models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfessorUserDetailsTest {

    private Professor professor;
    private ProfessorUserDetails userDetails;

    @BeforeEach
    void setUp() {
        professor = new Professor();
        professor.setProfessor_id(42L);
        professor.setEmail("professor@email.com");
        professor.setSenha("123456");

        userDetails = new ProfessorUserDetails(professor);
    }

    @Test
    void deveRetornarEmailComoUsername() {
        assertThat(userDetails.getUsername()).isEqualTo(professor.getEmail());
    }

    @Test
    void deveRetornarSenhaCorreta() {
        assertThat(userDetails.getPassword()).isEqualTo(professor.getSenha());
    }

    @Test
    void deveRetornarIdDoProfessor() {
        assertThat(userDetails.getProfessorId()).isEqualTo(professor.getProfessor_id());
    }

    @Test
    void deveRetornarListaVaziaDeAuthorities() {
        assertThat(userDetails.getAuthorities()).isEmpty();
    }

    @Test
    void deveEstarAtivoENaoExpirado() {
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();
    }
}
