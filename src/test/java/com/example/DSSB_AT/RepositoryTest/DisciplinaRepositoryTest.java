package com.example.DSSB_AT.RepositoryTest;

import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Repository.AlunoRepository;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import com.example.DSSB_AT.TestAuditingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestAuditingConfig.class)
public class DisciplinaRepositoryTest {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Test
    void DeveSalvarERecuperarADisciplina(){
        Disciplina disciplina = Disciplina.builder()
                .nome("Matemática Aplicada 1")
                .codigo("M-AP1")
                .build();

        disciplinaRepository.save(disciplina);

        Optional<Disciplina> disciplinaRecuperada = disciplinaRepository.findById(disciplina.getDisciplina_id());

        assertTrue(disciplinaRecuperada.isPresent());
        assertEquals("Matemática Aplicada 1", disciplinaRecuperada.get().getNome());
    }
}
