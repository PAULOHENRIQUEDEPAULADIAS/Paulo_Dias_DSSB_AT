package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.AtualizarNotasRequestDTO;
import com.example.DSSB_AT.DTO.MatriculaDetalheResponseDTO;
import com.example.DSSB_AT.DTO.MatriculaRequestDTO;
import com.example.DSSB_AT.DTO.MatriculaResponseDTO;
import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Models.Matricula;
import com.example.DSSB_AT.Models.Turma;
import com.example.DSSB_AT.Repository.AlunoRepository;
import com.example.DSSB_AT.Repository.MatriculaRepository;
import com.example.DSSB_AT.Repository.TurmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MatriculaControllerTest {

    private MatriculaRepository matriculaRepository;
    private AlunoRepository alunoRepository;
    private TurmaRepository turmaRepository;
    private MatriculaController matriculaController;

    @BeforeEach
    void setup() {
        matriculaRepository = mock(MatriculaRepository.class);
        alunoRepository = mock(AlunoRepository.class);
        turmaRepository = mock(TurmaRepository.class);

        matriculaController = new MatriculaController(
                matriculaRepository, alunoRepository, turmaRepository
        );
    }

    @Test
    void deveCriarMatricula() {
        MatriculaRequestDTO dto = new MatriculaRequestDTO();
        dto.setAlunoId(1L);
        dto.setTurmaId(10L);

        Aluno aluno = new Aluno();
        aluno.setAluno_id(1L);
        Turma turma = new Turma();
        turma.setTurma_id(10L);

        Matricula matricula = new Matricula();
        matricula.setMatricula_id(100L);
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(turmaRepository.findById(10L)).thenReturn(Optional.of(turma));
        when(matriculaRepository.save(any())).thenReturn(matricula);

        ResponseEntity<MatriculaResponseDTO> response = matriculaController.create(dto);

        assertThat(response.getBody()).isNotNull();
        verify(matriculaRepository, times(1)).save(any(Matricula.class));
    }

    @Test
    void deveAtualizarNotas() {
        Aluno aluno1 = new Aluno(); aluno1.setAluno_id(1L); aluno1.setNome("João");
        Turma turma1 = new Turma(); turma1.setTurma_id(10L);

        Matricula m1 = new Matricula();
        m1.setMatricula_id(1L);
        m1.setAluno(aluno1);
        m1.setTurma(turma1);

        AtualizarNotasRequestDTO dto = new AtualizarNotasRequestDTO();
        dto.setNota1(8.0);
        dto.setNota2(8.0);
        dto.setNota3(8.0);

        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(m1));
        when(matriculaRepository.save(m1)).thenReturn(m1);

        ResponseEntity<?> response = matriculaController.atualizarNotas(1L, dto);

        assertThat(response.getBody()).isInstanceOf(MatriculaDetalheResponseDTO.class);
        MatriculaDetalheResponseDTO detalhes = (MatriculaDetalheResponseDTO) response.getBody();
        assert detalhes != null;
        assertThat(detalhes.getNota1()).isEqualTo(8.0);
        assertThat(detalhes.getNota2()).isEqualTo(8.0);
        assertThat(detalhes.getNota3()).isEqualTo(8.0);
        verify(matriculaRepository).save(m1);
    }

    @Test
    void deveRetornarNotFoundAoAtualizarNotasDeMatriculaInexistente() {
        AtualizarNotasRequestDTO dto = new AtualizarNotasRequestDTO();
        dto.setNota1(7.5);

        when(matriculaRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = matriculaController.atualizarNotas(999L, dto);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void deveBuscarMatriculaPorId() {
        Aluno aluno1 = new Aluno(); aluno1.setAluno_id(1L); aluno1.setNome("João");
        Turma turma1 = new Turma(); turma1.setTurma_id(10L);

        Matricula m1 = new Matricula();
        m1.setMatricula_id(1L);
        m1.setAluno(aluno1);
        m1.setTurma(turma1);


        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(m1));

        ResponseEntity<MatriculaDetalheResponseDTO> response = matriculaController.getById(1L);

        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void deveListarTodasMatriculas() {
        Aluno aluno1 = new Aluno(); aluno1.setAluno_id(1L); aluno1.setNome("João");
        Turma turma1 = new Turma(); turma1.setTurma_id(10L);

        Aluno aluno2 = new Aluno(); aluno2.setAluno_id(2L); aluno2.setNome("Maria");
        Turma turma2 = new Turma(); turma2.setTurma_id(20L);

        Matricula m1 = new Matricula();
        m1.setMatricula_id(1L);
        m1.setAluno(aluno1);
        m1.setTurma(turma1);

        Matricula m2 = new Matricula();
        m2.setMatricula_id(2L);
        m2.setAluno(aluno2);
        m2.setTurma(turma2);

        when(matriculaRepository.findAll()).thenReturn(List.of(m1, m2));

        List<MatriculaResponseDTO> lista = matriculaController.listAll();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getAlunoNome()).isEqualTo("João");
        assertThat(lista.get(1).getAlunoNome()).isEqualTo("Maria");
    }


    @Test
    void deveDeletarMatricula() {
        ResponseEntity<Void> response = matriculaController.delete(300L);

        verify(matriculaRepository, times(1)).deleteById(300L);
        assertThat(response.getStatusCode().value()).isEqualTo(204);
    }
}
