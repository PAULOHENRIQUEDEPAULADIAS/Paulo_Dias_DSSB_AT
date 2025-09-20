package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.DisciplinaRequestDTO;
import com.example.DSSB_AT.DTO.DisciplinaResponseDTO;
import com.example.DSSB_AT.Models.Disciplina;
import com.example.DSSB_AT.Repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaRepository disciplinaRepository;

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> create(@RequestBody DisciplinaRequestDTO dto) {
        Disciplina disciplina = dto.toEntity();
        disciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(new DisciplinaResponseDTO(disciplina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> getById(@PathVariable Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        return ResponseEntity.ok(new DisciplinaResponseDTO(disciplina));
    }

    @GetMapping
    public List<DisciplinaResponseDTO> listAll() {
        return disciplinaRepository.findAll().stream()
                .map(DisciplinaResponseDTO::new)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> update(@PathVariable Long id, @RequestBody DisciplinaRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        disciplina.setNome(dto.getNome());
        disciplina.setCodigo(dto.getCodigo());

        disciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(new DisciplinaResponseDTO(disciplina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disciplinaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
