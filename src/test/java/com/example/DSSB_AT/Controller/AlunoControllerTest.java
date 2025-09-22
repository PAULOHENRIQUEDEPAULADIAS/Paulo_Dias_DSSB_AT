package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.AlunoRequestDTO;
import com.example.DSSB_AT.DTO.AlunoResponseDTO;
import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AlunoControllerTest {

    private AlunoRepository alunoRepository;
    private AlunoController alunoController;

    @BeforeEach
    void setup() {
        alunoRepository = mock(AlunoRepository.class);
        alunoController = new AlunoController(alunoRepository);
    }

    @Test
    void deveCriarAluno() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setNome("João");
        dto.setCpf("12345678900");
        dto.setEmail("joao@email.com");

        Aluno saved = new Aluno();
        saved.setAluno_id(1L);
        saved.setNome(dto.getNome());
        saved.setCpf(dto.getCpf());
        saved.setEmail(dto.getEmail());

        when(alunoRepository.save(any())).thenReturn(saved);

        ResponseEntity<AlunoResponseDTO> response = alunoController.create(dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(alunoRepository, times(1)).save(any(Aluno.class));
    }

    @Test
    void deveBuscarAlunoPorId() {
        Aluno aluno = new Aluno();
        aluno.setAluno_id(1L);
        aluno.setNome("Maria");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        ResponseEntity<AlunoResponseDTO> response = alunoController.getById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Maria");
    }

    @Test
    void deveLancarErroQuandoAlunoNaoExistir() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            alunoController.getById(1L);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Aluno não encontrado");
        }
    }

    @Test
    void deveListarTodosAlunos() {
        Aluno a1 = new Aluno(); a1.setAluno_id(1L); a1.setNome("A1");
        Aluno a2 = new Aluno(); a2.setAluno_id(2L); a2.setNome("A2");

        when(alunoRepository.findAll()).thenReturn(List.of(a1, a2));

        List<AlunoResponseDTO> lista = alunoController.listAll();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getNome()).isEqualTo("A1");
    }

    @Test
    void deveAtualizarAluno() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setNome("Novo Nome");

        Aluno existing = new Aluno();
        existing.setAluno_id(1L);
        existing.setNome("Antigo");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(alunoRepository.save(any())).thenReturn(existing);

        ResponseEntity<AlunoResponseDTO> response = alunoController.update(1L, dto);

        assertThat(response.getBody().getNome()).isEqualTo("Novo Nome");
        verify(alunoRepository, times(1)).save(existing);
    }

    @Test
    void deveDeletarAluno() {
        ResponseEntity<Void> response = alunoController.delete(1L);

        verify(alunoRepository, times(1)).deleteById(1L);
        assertEquals(204, response.getStatusCode().value());
    }
}
