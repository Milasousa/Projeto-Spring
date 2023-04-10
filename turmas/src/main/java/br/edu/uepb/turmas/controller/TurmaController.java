package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.domain.Professor;
import br.edu.uepb.turmas.domain.Turma;
import br.edu.uepb.turmas.repository.AlunoRepository;
import br.edu.uepb.turmas.repository.ProfessorRepository;
import br.edu.uepb.turmas.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmarepository;
    @Autowired
    private AlunoRepository alunorepository;
    @Autowired
    private ProfessorRepository professorrepository;

    @GetMapping
    public List<Turma> getTurmas() {
        return turmarepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Turma> getTurmasById(@PathVariable Long id) {
        return turmarepository.findById(id);
    }

    @PostMapping
    public Turma criarTurma(@RequestBody Turma turma) {
        return turmarepository.save(turma);
    }

    @PutMapping("/{id}")
    public Turma atualizarTurma(@PathVariable("id") Long id, @RequestBody Turma turma) {
        try {
            if ((turmarepository.findById(id).get()) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não existe nenhuma Turma com esse identificador: " + id);
            }
            return turmarepository.save(turma);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe nenhuma Turma com esse identificador: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void apagarTurma(@PathVariable Long id) {
        try {
            turmarepository.delete(turmarepository.findById(id).get());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe nenhuma Turma com esse identificador: " + id);
        }
    }
    //@PatchMapping("/{turmaId}/matriculas/{alunoId}/turmas/{profId}")
    @PatchMapping("/{turmaId}/alunos/{alunoId}")
    public Turma vincularTurmaAluno(@PathVariable Long turmaId,@PathVariable Long alunoId) {
        Aluno aluno=alunorepository.findById(alunoId).get();
        Turma turma= turmarepository.findById(turmaId).get();
        turma.setAluno(aluno);
        return turmarepository.save(turma);
    }
    @PatchMapping("/{turmaId}/professores/{profId}")
    public Turma vincularTurmaProfessor(@PathVariable Long turmaId,@PathVariable Long profId) {
        Professor prof=professorrepository.findById(profId).get();
        Turma turma= turmarepository.findById(turmaId).get();
        turma.setProf(prof);
        return turmarepository.save(turma);
    }

    @PatchMapping("/{turmaId}/matriculas/{alunoId}/turmas/{profId}")
    public Turma vincularTurma(@PathVariable Long turmaId,@PathVariable Long alunoId,@PathVariable Long profId) {
        Professor prof=professorrepository.findById(profId).get();
        Aluno aluno=alunorepository.findById(alunoId).get();
        Turma turma= turmarepository.findById(turmaId).get();
        turma.setAluno(aluno);
        turma.setProf(prof);
        return turmarepository.save(turma);
    }
}
