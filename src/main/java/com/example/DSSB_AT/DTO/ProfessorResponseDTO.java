package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Professor;
import lombok.Data;

@Data
public class ProfessorResponseDTO {
    private Long id;
    private String nome;
    private String email;

    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getProfessor_id();
        this.nome = professor.getNome();
        this.email = professor.getEmail();
    }
}
