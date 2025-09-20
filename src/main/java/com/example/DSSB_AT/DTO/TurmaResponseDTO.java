package com.example.DSSB_AT.DTO;

import com.example.DSSB_AT.Models.Turma;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TurmaResponseDTO {
    private Long id;
    private DisciplinaResponseDTO disciplina;
    private ProfessorResponseDTO professor;
    private List<MatriculaResponseDTO> matriculas;

    public TurmaResponseDTO(Turma turma) {
        this.id = turma.getTurma_id();
        this.disciplina = new DisciplinaResponseDTO(turma.getDisciplina());
        this.professor = new ProfessorResponseDTO(turma.getProfessor());
        this.matriculas = turma.getMatriculas()
                .stream()
                .map(MatriculaResponseDTO::new)
                .collect(Collectors.toList());
    }
}
