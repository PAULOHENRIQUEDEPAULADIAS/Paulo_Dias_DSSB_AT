package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Matricula;
import lombok.Data;

@Data
public class MatriculaResponseDTO {
    private String alunoNome;
    private String resultado;

    public MatriculaResponseDTO(Matricula matricula) {
        this.alunoNome = matricula.getAluno().getNome();
        Double media = matricula.calcularMedia();
        if (media == null) {
            this.resultado = "Não foram lançadas todas as notas";
        } else {
            this.resultado = media >= 7.0 ? "Aprovado" : "Reprovado";
        }
    }
}