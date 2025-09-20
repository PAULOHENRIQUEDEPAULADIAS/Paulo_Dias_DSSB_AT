package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.AlunoRequestDTO;
import com.example.DSSB_AT.DTO.AlunoResponseDTO;
import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = dto.toEntity();
        aluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(new AlunoResponseDTO(aluno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> getById(@PathVariable Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        return ResponseEntity.ok(new AlunoResponseDTO(aluno));
    }

    @GetMapping
    public List<AlunoResponseDTO> listAll() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> update(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setNome(dto.getNome());
        aluno.setCpf(dto.getCpf());
        aluno.setEmail(dto.getEmail());
        aluno.setTelefone(dto.getTelefone());
        aluno.setEndereco(dto.getEndereco());

        aluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(new AlunoResponseDTO(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
