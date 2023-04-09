package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Aluno> getAlunosById(@PathVariable Long id) {
        return alunoRepository.findById(id);
    }

    @PostMapping
    public Aluno criarAlunos(@RequestBody Aluno aluno) {
            if (!(alunoRepository.findById(aluno.getIdMatricula()) != null)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já existe com estes dados");
            }else if((alunoRepository.findBynome(aluno.getEmail()) != null)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já existe com estes dados");
            }else{
                return alunoRepository.save(aluno);
            }
            

        }

    @PutMapping("/{id}")
    public Aluno atualizarAlunos(@PathVariable("id") Long id, @RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @DeleteMapping("/{id}")
    public void apagarAlunos(@PathVariable Long id) {
        try {
            alunoRepository.delete(alunoRepository.findById(id).get());
        
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
    }
}
