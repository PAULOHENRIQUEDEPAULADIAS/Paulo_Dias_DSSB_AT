package com.example.DSSB_AT.Controller;

import com.example.DSSB_AT.DTO.AtualizarNotasRequestDTO;
import com.example.DSSB_AT.DTO.MatriculaDetalheResponseDTO;
import com.example.DSSB_AT.DTO.MatriculaRequestDTO;
import com.example.DSSB_AT.DTO.MatriculaResponseDTO;
import com.example.DSSB_AT.Models.Aluno;
import com.example.DSSB_AT.Models.Matricula;
import com.example.DSSB_AT.Models.Turma;
import com.example.DSSB_AT.Repository.AlunoRepository;
import com.example.DSSB_AT.Repository.MatriculaRepository;
import com.example.DSSB_AT.Repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> create(@RequestBody MatriculaRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Matricula matricula = dto.toEntity(aluno, turma);
        matricula = matriculaRepository.save(matricula);

        return ResponseEntity.ok(new MatriculaResponseDTO(matricula));
    }

    @PutMapping("/{id}/notas")
    public ResponseEntity<?> atualizarNotas(@PathVariable Long id,
                                            @RequestBody AtualizarNotasRequestDTO dto) {
        return matriculaRepository.findById(id)
                .map(matricula -> {
                    if (dto.getNota1() != null) {
                        matricula.setNota1(dto.getNota1());
                    }
                    if (dto.getNota2() != null) {
                        matricula.setNota2(dto.getNota2());
                    }
                    if (dto.getNota3() != null) {
                        matricula.setNota3(dto.getNota3());
                    }

                    matriculaRepository.save(matricula);
                    return ResponseEntity.ok(new MatriculaDetalheResponseDTO(matricula));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDetalheResponseDTO> getById(@PathVariable Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
        return ResponseEntity.ok(new MatriculaDetalheResponseDTO(matricula));
    }

    @GetMapping
    public List<MatriculaResponseDTO> listAll() {
        return matriculaRepository.findAll().stream()
                .map(MatriculaResponseDTO::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
