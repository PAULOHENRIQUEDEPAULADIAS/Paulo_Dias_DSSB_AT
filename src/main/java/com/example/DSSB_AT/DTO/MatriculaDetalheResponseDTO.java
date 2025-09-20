package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Matricula;
import lombok.Data;

@Data
public class MatriculaDetalheResponseDTO {
    private String alunoNome;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double media;
    private String resultado;

    public MatriculaDetalheResponseDTO(Matricula matricula) {
        this.alunoNome = matricula.getAluno().getNome();
        this.nota1 = matricula.getNota1();
        this.nota2 = matricula.getNota2();
        this.nota3 = matricula.getNota3();

        Double mediaCalculada = matricula.calcularMedia();
        this.media = mediaCalculada;

        if (mediaCalculada == null) {
            this.resultado = "Não foram lançadas todas as notas";
        } else {
            this.resultado = mediaCalculada >= 7.0 ? "Aprovado" : "Reprovado";
        }
    }
}
