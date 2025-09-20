package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Disciplina;
import lombok.Data;

@Data
public class DisciplinaRequestDTO {
    private String nome;
    private String codigo;

    public Disciplina toEntity() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(this.nome);
        disciplina.setCodigo(this.codigo);
        return disciplina;
    }
}
