package com.example.DSSB_AT.RepositoryTest;


import com.example.DSSB_AT.Models.*;
import com.example.DSSB_AT.Repository.*;
import com.example.DSSB_AT.TestAuditingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(TestAuditingConfig.class)
class MatriculaRepositoryTest {

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    TurmaRepository turmaRepository;

    private Aluno aluno;
    private Disciplina disciplina;
    private Professor professor;
    private Turma turma;

    @BeforeEach
    void setup() {
        aluno = Aluno.builder()
                .nome("Paulo")
                .cpf("111.222.333-44")
                .email("paulo@email.com")
                .telefone("11 99999-8888")
                .build();
        alunoRepository.save(aluno);

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

        turma = Turma.builder()
                .disciplina(disciplina)
                .professor(professor)
                .build();
        turmaRepository.save(turma);
    }

    @Test
    void deveSalvarMatriculaComAlunoETurma() {
        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .turma(turma)
                .build();

        matriculaRepository.save(matricula);

        assertNotNull(matricula.getMatricula_id());
        assertEquals(aluno.getNome(), matricula.getAluno().getNome());
        assertEquals(disciplina.getNome(), matricula.getTurma().getDisciplina().getNome());
    }

    @Test
    void deveGerarAMediaDeNotas() {
        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .turma(turma)
                .nota1(8.0)
                .nota2(8.0)
                .nota3(8.0)
                .build();

        matriculaRepository.save(matricula);

        Double media = matricula.calcularMedia();
        assertEquals(8.0, media);
    }
}