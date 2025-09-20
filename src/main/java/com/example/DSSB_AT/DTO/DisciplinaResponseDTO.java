package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Disciplina;
import lombok.Data;

@Data
public class DisciplinaResponseDTO {
    private Long id;
    private String nome;
    private String codigo;

    public DisciplinaResponseDTO(Disciplina disciplina) {
        this.id = disciplina.getDisciplina_id();
        this.nome = disciplina.getNome();
        this.codigo = disciplina.getCodigo();
    }
}
