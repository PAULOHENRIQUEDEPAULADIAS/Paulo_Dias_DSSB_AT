package com.example.DSSB_AT.RepositoryTest;

import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Repository.AlunoRepository;
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
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    void DeveSalvarERecuperarAluno(){
        Aluno aluno = Aluno.builder()
                .nome("Paulo Henrique de Paula Dias")
                .cpf("555.444.333-22")
                .email("paulo@gmail.com")
                .telefone("11 95555-2222")
                .build();

        alunoRepository.save(aluno);

        Optional<Aluno> alunoRecuperado = alunoRepository.findById(aluno.getAluno_id());

        assertTrue(alunoRecuperado.isPresent());
        assertEquals("Paulo Henrique de Paula Dias", alunoRecuperado.get().getNome());
    }
}
