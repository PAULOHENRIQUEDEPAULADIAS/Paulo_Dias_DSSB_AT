package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Aluno;
import lombok.Data;

@Data
public class AlunoResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;

    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getAluno_id();
        this.nome = aluno.getNome();
        this.cpf = aluno.getCpf();
        this.email = aluno.getEmail();
        this.telefone = aluno.getTelefone();
        this.endereco = aluno.getEndereco();
    }
}
