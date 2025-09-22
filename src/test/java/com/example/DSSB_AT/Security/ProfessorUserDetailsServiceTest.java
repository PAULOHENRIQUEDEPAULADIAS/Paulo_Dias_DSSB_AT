package com.example.DSSB_AT.Security;

import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProfessorUserDetailsServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorUserDetailsService service;

    private Professor professor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        professor = new Professor();
        professor.setProfessor_id(1L);
        professor.setEmail("professor@email.com");
        professor.setSenha("123456");
    }

    @Test
    void deveCarregarProfessorQuandoExistir() {
        // Arrange
        when(professorRepository.findByEmail("professor@email.com"))
                .thenReturn(Optional.of(professor));

        // Act
        UserDetails userDetails = service.loadUserByUsername("professor@email.com");

        // Assert
        assertThat(userDetails).isInstanceOf(ProfessorUserDetails.class);
        assertThat(userDetails.getUsername()).isEqualTo(professor.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(professor.getSenha());
    }

    @Test
    void deveLancarExcecaoQuandoProfessorNaoExistir() {
        // Arrange
        when(professorRepository.findByEmail("inexistente@email.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("inexistente@email.com"));
    }
}
