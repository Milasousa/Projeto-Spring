package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.turmas.domain.ErroRespostaGenerica;
import br.edu.uepb.turmas.domain.Turma;
import br.edu.uepb.turmas.dto.TurmaDTO;
import br.edu.uepb.turmas.mapper.TurmaMapper;
import br.edu.uepb.turmas.services.TurmaService;
import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    @Autowired
    private TurmaMapper turmaMapper;

    @Autowired
    private TurmaService turmaService;


    @GetMapping
    public List<TurmaDTO> getTurmas() {
        List<Turma> turmas = turmaService.listAllTurmas();
        return turmas.stream()
                .map(turmaMapper::convertToTurmaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTurmasById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(turmaMapper.convertToTurmaDTO(turmaService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenerica(e.getMessage()));
        }
    }
    @PostMapping
    public ResponseEntity<?> criarTurma(@RequestBody TurmaDTO  turmaDTO) {
            Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
            return new ResponseEntity<>(turmaService.criarTurma(turma), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTurma(@PathVariable("id") Long id, @RequestBody TurmaDTO  turmaDTO) throws NotFoundException {
        try {
            Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
            return new ResponseEntity<>(turmaMapper.convertToTurmaDTO(turmaService.atualizarTurma(id,turma)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));

        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> apagarTurma(@PathVariable ("id")  Long id) {
        try {
            turmaService.apagarTurma(id);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);

        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));

        }
    }

    @PatchMapping("/{turmaId}/alunos/{alunoId}")
    public ResponseEntity<?> vincularTurmaAluno(@PathVariable("turmaId") Long turmaId, @PathVariable("alunoId") Long alunoId) throws NotFoundException {
try {
    return new ResponseEntity<>(turmaService.vincularTurmaAluno(turmaId, alunoId), HttpStatus.CREATED);

} catch (NotFoundException e) {
    return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));

}
    }

    
    @PatchMapping("/{turmaId}/professores/{profId}")
    public ResponseEntity<?> vincularTurmaProfessor(@PathVariable("turmaId") Long turmaId, @PathVariable("profId") Long profId)throws NotFoundException {
try {
    return new ResponseEntity<>(turmaService.vincularTurmaProfessor(turmaId, profId), HttpStatus.CREATED);

} catch (NotFoundException e) {
    return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));
}
    }

    @PatchMapping("/{turmaId}/matriculas/{alunoId}/turmas/{profId}")
    public ResponseEntity<?> vincularTurma(@PathVariable("turmaId") Long turmaId, @PathVariable("alunoId")  Long alunoId, @PathVariable("profId") Long profId) throws NotFoundException {
try {
    return new ResponseEntity<>(turmaService.vincularTurma(turmaId,alunoId,profId), HttpStatus.CREATED);

} catch (Exception e) {
    return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));
}
    }
}