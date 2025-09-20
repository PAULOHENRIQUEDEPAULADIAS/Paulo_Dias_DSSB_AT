package com.example.DSSB_AT.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricula_id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double notaFinal;

    public Double calcularMedia() {
        if (nota1 == null || nota2 == null || nota3 == null) {
            return null;
        }
        return (nota1 + nota2 + nota3) / 3.0;
    }
}
