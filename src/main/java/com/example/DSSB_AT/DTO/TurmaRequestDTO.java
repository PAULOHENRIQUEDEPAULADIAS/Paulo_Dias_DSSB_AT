package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Models.Turma;
import lombok.Data;

@Data
public class TurmaRequestDTO {
    private Long professorId;
    private Long disciplinaId;

    public Turma toEntity(Professor professor, Disciplina disciplina) {
        Turma turma = new Turma();
        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);
        return turma;
    }
}
