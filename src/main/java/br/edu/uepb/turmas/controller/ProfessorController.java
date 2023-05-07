package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uepb.turmas.domain.ErroRespostaGenerica;
import br.edu.uepb.turmas.domain.Professor;
import br.edu.uepb.turmas.dto.ProfessorDTO;
import br.edu.uepb.turmas.exceptions.DadosIguaisException;
import br.edu.uepb.turmas.mapper.ProfessorMapper;
import br.edu.uepb.turmas.services.ProfessorService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<ProfessorDTO> getProfessores() {
        List<Professor> professores = professorService.listAllProfessores();
        return professores.stream()
                .map(professorMapper::convertToProfessorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(professorMapper.convertToProfessorDTO(professorService.findById(id)),
                    HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenerica(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criarProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
            return new ResponseEntity<>(professorService.criarProfessor(professor), HttpStatus.CREATED);
        } catch (DadosIguaisException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenerica(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDTO professorDTO)
            throws NotFoundException {
        try {
            Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
            return new ResponseEntity<>(
                    professorMapper.convertToProfessorDTO(professorService.atualizarProfessor(id, professor)),
                    HttpStatus.OK);

        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarProfessor(@PathVariable Long id) throws NotFoundException {
        professorService.apagarProfessor(id);
        try {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenerica(e.getMessage()));
        }
    }
}
