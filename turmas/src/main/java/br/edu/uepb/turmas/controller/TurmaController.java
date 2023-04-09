package br.edu.uepb.turmas.controller;
import java.util.List;
import java.util.Optional;
import br.edu.uepb.turmas.domain.Turma;
import br.edu.uepb.turmas.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmarepository;
    private AlunoController alunoController;
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
        return turmarepository.save(turma);
    }

    @DeleteMapping("/{id}")
    public void apagarTurma(@PathVariable Long id) {
        turmarepository.delete(turmarepository.findById(id).get());
    }
}
