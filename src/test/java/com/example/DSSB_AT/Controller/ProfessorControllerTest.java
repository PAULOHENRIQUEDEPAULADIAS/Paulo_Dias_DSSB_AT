package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.ProfessorRequestDTO;
import com.example.DSSB_AT.DTO.ProfessorResponseDTO;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProfessorControllerTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorController professorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProfessor() {
        ProfessorRequestDTO dto = new ProfessorRequestDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@email.com");
        dto.setSenha("123");

        Professor professor = dto.toEntity();
        professor.setProfessor_id(1L);

        when(professorRepository.save(any(Professor.class))).thenReturn(professor);

        ResponseEntity<ProfessorResponseDTO> response = professorController.create(dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Maria");
        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void deveBuscarProfessorPorId() {
        Professor professor = new Professor();
        professor.setProfessor_id(1L);
        professor.setNome("Carlos");

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        ResponseEntity<ProfessorResponseDTO> response = professorController.getById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Carlos");
        verify(professorRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoProfessorNaoEncontradoPorId() {
        when(professorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> professorController.getById(99L));
    }

    @Test
    void deveListarTodosProfessores() {
        Professor p1 = new Professor(); p1.setProfessor_id(1L); p1.setNome("João");
        Professor p2 = new Professor(); p2.setProfessor_id(2L); p2.setNome("Ana");

        when(professorRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProfessorResponseDTO> lista = professorController.listAll();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getNome()).isEqualTo("João");
        assertThat(lista.get(1).getNome()).isEqualTo("Ana");
        verify(professorRepository).findAll();
    }

    @Test
    void deveAtualizarProfessor() {
        Professor professor = new Professor();
        professor.setProfessor_id(1L);
        professor.setNome("Pedro");
        professor.setEmail("pedro@email.com");
        professor.setSenha("123");

        ProfessorRequestDTO dto = new ProfessorRequestDTO();
        dto.setNome("Pedro Atualizado");
        dto.setEmail("pedro.novo@email.com");
        dto.setSenha("456");

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);

        ResponseEntity<ProfessorResponseDTO> response = professorController.update(1L, dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Pedro Atualizado");
        assertThat(response.getBody().getEmail()).isEqualTo("pedro.novo@email.com");
        verify(professorRepository).save(professor);
    }

    @Test
    void deveDeletarProfessor() {
        doNothing().when(professorRepository).deleteById(1L);

        ResponseEntity<Void> response = professorController.delete(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(professorRepository).deleteById(1L);
    }
}
