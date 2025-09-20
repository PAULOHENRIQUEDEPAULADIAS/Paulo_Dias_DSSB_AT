package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Professor;
import lombok.Data;

@Data
public class ProfessorRequestDTO {
    private String nome;
    private String email;
    private String senha;

    public Professor toEntity() {
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setEmail(this.email);
        professor.setSenha(this.senha);
        return professor;
    }
}
