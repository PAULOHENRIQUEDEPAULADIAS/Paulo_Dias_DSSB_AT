package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.TurmaRequestDTO;
import com.example.DSSB_AT.DTO.TurmaResponseDTO;
import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Models.Turma;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import com.example.DSSB_AT.Repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> create(@RequestBody TurmaRequestDTO dto) {
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        Turma turma = dto.toEntity(professor, disciplina);
        turma = turmaRepository.save(turma);

        return ResponseEntity.ok(new TurmaResponseDTO(turma));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> getById(@PathVariable Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        return ResponseEntity.ok(new TurmaResponseDTO(turma));
    }

    @GetMapping
    public List<TurmaResponseDTO> listAll() {
        return turmaRepository.findAll().stream()
                .map(TurmaResponseDTO::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turmaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
