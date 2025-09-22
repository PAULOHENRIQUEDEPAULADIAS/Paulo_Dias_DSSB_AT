package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.DisciplinaRequestDTO;
import com.example.DSSB_AT.DTO.DisciplinaResponseDTO;
import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DisciplinaControllerTest {

    private DisciplinaRepository disciplinaRepository;
    private DisciplinaController disciplinaController;

    @BeforeEach
    void setup() {
        disciplinaRepository = mock(DisciplinaRepository.class);
        disciplinaController = new DisciplinaController(disciplinaRepository);
    }

    @Test
    void deveCriarDisciplina() {
        DisciplinaRequestDTO dto = new DisciplinaRequestDTO();
        dto.setNome("Spring Boot");
        dto.setCodigo("SB001");

        Disciplina saved = new Disciplina();
        saved.setDisciplina_id(1L);
        saved.setNome(dto.getNome());
        saved.setCodigo(dto.getCodigo());

        when(disciplinaRepository.save(any())).thenReturn(saved);

        ResponseEntity<DisciplinaResponseDTO> response = disciplinaController.create(dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getNome()).isEqualTo("Spring Boot");
        verify(disciplinaRepository, times(1)).save(any(Disciplina.class));
    }

    @Test
    void deveBuscarDisciplinaPorId() {
        Disciplina disciplina = new Disciplina();
        disciplina.setDisciplina_id(1L);
        disciplina.setNome("Matemática");

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        ResponseEntity<DisciplinaResponseDTO> response = disciplinaController.getById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Matemática");
    }

    @Test
    void deveLancarErroQuandoDisciplinaNaoExistir() {
        when(disciplinaRepository.findById(99L)).thenReturn(Optional.empty());

        try {
            disciplinaController.getById(99L);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Disciplina não encontrada");
        }
    }

    @Test
    void deveListarTodasDisciplinas() {
        Disciplina d1 = new Disciplina(); d1.setDisciplina_id(1L); d1.setNome("Java");
        Disciplina d2 = new Disciplina(); d2.setDisciplina_id(2L); d2.setNome("Python");

        when(disciplinaRepository.findAll()).thenReturn(List.of(d1, d2));

        List<DisciplinaResponseDTO> lista = disciplinaController.listAll();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(1).getNome()).isEqualTo("Python");
    }

    @Test
    void deveAtualizarDisciplina() {
        DisciplinaRequestDTO dto = new DisciplinaRequestDTO();
        dto.setNome("Java Avançado");
        dto.setCodigo("JAV002");

        Disciplina existente = new Disciplina();
        existente.setDisciplina_id(1L);
        existente.setNome("Java Básico");
        existente.setCodigo("JAV001");

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(disciplinaRepository.save(any())).thenReturn(existente);

        ResponseEntity<DisciplinaResponseDTO> response = disciplinaController.update(1L, dto);

        assertThat(response.getBody().getNome()).isEqualTo("Java Avançado");
        assertThat(response.getBody().getCodigo()).isEqualTo("JAV002");
        verify(disciplinaRepository, times(1)).save(existente);
    }

    @Test
    void deveDeletarDisciplina() {
        ResponseEntity<Void> response = disciplinaController.delete(1L);

        verify(disciplinaRepository, times(1)).deleteById(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(204);
    }
}
