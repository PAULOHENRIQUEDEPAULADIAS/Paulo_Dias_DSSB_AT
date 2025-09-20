package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Aluno;
import lombok.Data;

@Data
public class AlunoRequestDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;

    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        aluno.setNome(this.nome);
        aluno.setCpf(this.cpf);
        aluno.setEmail(this.email);
        aluno.setTelefone(this.telefone);
        aluno.setEndereco(this.endereco);
        return aluno;
    }
}
