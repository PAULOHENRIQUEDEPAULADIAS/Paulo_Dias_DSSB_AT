package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.TurmaRequestDTO;
import com.example.DSSB_AT.DTO.TurmaResponseDTO;
import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Models.Turma;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import com.example.DSSB_AT.Repository.TurmaRepository;
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

class TurmaControllerTest {

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private TurmaController turmaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarTurma() {
        TurmaRequestDTO dto = new TurmaRequestDTO();
        dto.setProfessorId(1L);
        dto.setDisciplinaId(2L);

        Professor professor = new Professor();
        professor.setProfessor_id(1L);
        professor.setNome("Carlos");

        Disciplina disciplina = new Disciplina();
        disciplina.setDisciplina_id(2L);
        disciplina.setNome("Matemática");

        Turma turma = new Turma();
        turma.setTurma_id(10L);
        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(disciplinaRepository.findById(2L)).thenReturn(Optional.of(disciplina));
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        ResponseEntity<TurmaResponseDTO> response = turmaController.create(dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(10L);
        assertThat(response.getBody().getProfessor().getId()).isEqualTo(1L);
        assertThat(response.getBody().getDisciplina().getId()).isEqualTo(2L);
        verify(turmaRepository).save(any(Turma.class));
    }

    @Test
    void deveLancarExcecaoQuandoProfessorNaoEncontrado() {
        TurmaRequestDTO dto = new TurmaRequestDTO();
        dto.setProfessorId(1L);
        dto.setDisciplinaId(2L);

        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> turmaController.create(dto));
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaNaoEncontrada() {
        TurmaRequestDTO dto = new TurmaRequestDTO();
        dto.setProfessorId(1L);
        dto.setDisciplinaId(2L);

        Professor professor = new Professor();
        professor.setProfessor_id(1L);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(disciplinaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> turmaController.create(dto));
    }

    @Test
    void deveBuscarTurmaPorId() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Spring Boot");
        Professor professor = new Professor();
        professor.setNome("Professor");

        Turma turma = new Turma();
        turma.setTurma_id(10L);
        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);

        when(turmaRepository.findById(10L)).thenReturn(Optional.of(turma));

        ResponseEntity<TurmaResponseDTO> response = turmaController.getById(10L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(10L);
        verify(turmaRepository).findById(10L);
    }

    @Test
    void deveLancarExcecaoQuandoTurmaNaoEncontrada() {
        when(turmaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> turmaController.getById(99L));
    }

    @Test
    void deveListarTodasTurmas() {

        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Spring Boot");
        Professor professor = new Professor();
        professor.setNome("Professor");

        Turma t1 = new Turma(); t1.setTurma_id(1L);
        Turma t2 = new Turma(); t2.setTurma_id(2L);
        t1.setDisciplina(disciplina);
        t2.setDisciplina(disciplina);
        t1.setProfessor(professor);
        t2.setProfessor(professor);

        when(turmaRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TurmaResponseDTO> lista = turmaController.listAll();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getId()).isEqualTo(1L);
        assertThat(lista.get(1).getId()).isEqualTo(2L);
        verify(turmaRepository).findAll();
    }

    @Test
    void deveDeletarTurma() {
        doNothing().when(turmaRepository).deleteById(1L);

        ResponseEntity<Void> response = turmaController.delete(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(turmaRepository).deleteById(1L);
    }
}
