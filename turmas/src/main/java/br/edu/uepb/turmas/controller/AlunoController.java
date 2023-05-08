package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.dto.AlunoDTO;
import br.edu.uepb.turmas.dto.ErroRespostaGenericaDTO;
import br.edu.uepb.turmas.exceptions.DadosIguaisException;
import br.edu.uepb.turmas.mapper.AlunoMapper;
import br.edu.uepb.turmas.services.AlunoService;
import javassist.NotFoundException;

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

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> getAlunos() {
        List<Aluno> alunos = alunoService.listAllAlunos();
        return alunos.stream()
                .map(alunoMapper::convertToAlunoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlunosById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(alunoMapper.convertToAlunoDTO(alunoService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenericaDTO(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criarAlunos(@RequestBody AlunoDTO alunoDTO) {
        try {
            Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
            return new ResponseEntity<>(alunoService.criarAlunos(aluno), HttpStatus.CREATED);
        } catch (DadosIguaisException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenericaDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAlunos(@PathVariable("id") Long id, @RequestBody AlunoDTO alunoDTO)
            throws NotFoundException {
        try {
            Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
            return new ResponseEntity<>(alunoMapper.convertToAlunoDTO(alunoService.atualizarAlunos(id, aluno)),
                    HttpStatus.OK);
        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarAlunos(@PathVariable Long id) throws NotFoundException {

        try {
            alunoService.apagarAlunos(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));
        }
    }
}
