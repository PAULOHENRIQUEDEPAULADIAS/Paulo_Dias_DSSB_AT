package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Models.Matricula;
import com.example.DSSB_AT.Models.Turma;
import lombok.Data;

@Data
public class MatriculaRequestDTO {
    private Long alunoId;
    private Long turmaId;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double notaFinal;

    public Matricula toEntity(Aluno aluno, Turma turma) {
        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setNota1(this.nota1);
        matricula.setNota2(this.nota2);
        matricula.setNota3(this.nota3);
        matricula.setNotaFinal(this.notaFinal);
        return matricula;
    }
}
