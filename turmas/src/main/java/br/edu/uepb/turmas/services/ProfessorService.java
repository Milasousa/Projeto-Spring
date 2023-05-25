package br.edu.uepb.turmas.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;
import br.edu.uepb.turmas.domain.Professor;
import br.edu.uepb.turmas.exceptions.DadosIguaisException;
import br.edu.uepb.turmas.repository.ProfessorRepository;


@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    
    public boolean verificarPorId(Long id) {
        return professorRepository.existsById(id);
    }

    public boolean verificarPorEmail(String email) {
        return professorRepository.existsByemail(email);
    }
    
    public List<Professor> listAllProfessores() {
        return professorRepository.findAll();
    } 
    public Professor criarProfessor(Professor professor) throws DadosIguaisException {
        if ((verificarPorId(professor.getId())) || (verificarPorEmail(professor.getEmail())))
            throw new DadosIguaisException("Já existe um Professor com essa matricula ou e-mail");
        return professorRepository.save(professor);

    }
    public Professor atualizarProfessor( Long id, Professor professor) throws NotFoundException{
        try {
            if ((professorRepository.findById(id).get()) == null) {
                throw new NotFoundException ("Não foi encontrado nenhum Professor com essa matricula: "+ id);

            }
            return professorRepository.save(professor);
        } catch (NoSuchElementException e) {
            throw new  NotFoundException ("Não foi encontrado nenhum Professor com essa matricula: "+ id);
        }

    }

    public Professor findById(Long id) throws NotFoundException {
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum Professor com essa matricula!"));
    }

    public void apagarProfessor(Long id) throws NotFoundException{
        try {
            professorRepository.delete(professorRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            throw new  NotFoundException ("Não foi encontrado nenhum Professor com essa matricula: "+ id);
        }
    }

}
