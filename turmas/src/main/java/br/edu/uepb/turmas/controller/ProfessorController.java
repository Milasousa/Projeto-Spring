package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.uepb.turmas.domain.Professor;
import br.edu.uepb.turmas.repository.ProfessorRepository;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorrepository;

    @GetMapping
    public List<Professor> getProfessores() {
        return professorrepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Professor> getProfessoreById(@PathVariable Long id) {
        return professorrepository.findById(id);
    }

    @PostMapping
    public Professor criarProfessor(@RequestBody Professor prof) {
        return professorrepository.save(prof);
    }

    @PutMapping("/{id}")
    public Professor atualizarProfessor(@PathVariable("id") Long id, @RequestBody Professor prof) {
        return professorrepository.save(prof);
    }

    @DeleteMapping("/{id}")
    public void apagarProfessor(@PathVariable Long id) {
        professorrepository.delete(professorrepository.findById(id).get());
    }
}
