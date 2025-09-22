package com.example.DSSB_AT.RepositoryTest;

import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Models.Turma;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import com.example.DSSB_AT.Repository.TurmaRepository;
import com.example.DSSB_AT.TestAuditingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestAuditingConfig.class)
public class TurmaRepositoryTest {

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    ProfessorRepository professorRepository;

    private Disciplina disciplina;
    private Professor professor;
    private Turma turma;

    @BeforeEach
    void setup() {
        disciplina = Disciplina.builder()
                .nome("Spring Boot")
                .codigo("SB001")
                .build();
        disciplinaRepository.save(disciplina);

        professor = Professor.builder()
                .nome("Carlos")
                .email("carlos@gmail.com")
                .build();
        professorRepository.save(professor);
    }

    @Test
    void deveSalvarERetornarATurma(){
        turma = Turma.builder()
                .disciplina(disciplina)
                .professor(professor)
                .build();
        turmaRepository.save(turma);

        Optional<Turma> turmaRecuperada = turmaRepository.findById(turma.getTurma_id());

        assertTrue(turmaRecuperada.isPresent());
        assertEquals(3, turmaRecuperada.get().getTurma_id());
        assertEquals("Carlos", turmaRecuperada.get().getProfessor().getNome());
        assertEquals("Spring Boot", turmaRecuperada.get().getDisciplina().getNome());
    }

}
