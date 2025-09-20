package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.ProfessorRequestDTO;
import com.example.DSSB_AT.DTO.ProfessorResponseDTO;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = dto.toEntity();
        professor = professorRepository.save(professor);
        return ResponseEntity.ok(new ProfessorResponseDTO(professor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getById(@PathVariable Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return ResponseEntity.ok(new ProfessorResponseDTO(professor));
    }

    @GetMapping
    public List<ProfessorResponseDTO> listAll() {
        return professorRepository.findAll().stream()
                .map(ProfessorResponseDTO::new)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@PathVariable Long id, @RequestBody ProfessorRequestDTO dto) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setSenha(dto.getSenha());

        professor = professorRepository.save(professor);
        return ResponseEntity.ok(new ProfessorResponseDTO(professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        professorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
