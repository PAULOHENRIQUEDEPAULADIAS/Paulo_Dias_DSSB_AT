package com.example.DSSB_AT.RepositoryTest;

import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Repository.ProfessorRepository;
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
public class ProfessorRepositoryTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    void deveSalvarERetornarOProfessor(){
        Professor professor = Professor.builder()
                .nome("Carlos")
                .email("carlos@gmail.com")
                .build();
        professorRepository.save(professor);

        Optional<Professor> professorRecuperado = professorRepository.findById(professor.getProfessor_id());

        assertTrue(professorRecuperado.isPresent());
        assertEquals("Carlos", professorRecuperado.get().getNome());
    }

}
