package br.edu.uepb.turmas.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;
import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.exceptions.DadosIguaisException;
import br.edu.uepb.turmas.repository.AlunoRepository;


@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    
    public boolean verificarPorId(Long id) {
        return alunoRepository.existsById(id);
    }

    public boolean verificarPorEmail(String email) {
        return alunoRepository.existsByemail(email);
    }
    public List<Aluno> listAllAlunos() {
        return alunoRepository.findAll();
    }
    public Aluno criarAlunos(Aluno aluno) throws DadosIguaisException {
        if ((verificarPorId(aluno.getIdMatricula())) || (verificarPorEmail(aluno.getEmail())))
            throw new DadosIguaisException("Já existe um Aluno com essa matricula ou e-mail");
        return alunoRepository.save(aluno);

    }
    public Aluno atualizarAlunos( Long id, Aluno aluno) throws NotFoundException{
        try {
            if ((alunoRepository.findById(id).get()) == null) {
                throw new NotFoundException ("Não foi encontrado nenhum Aluno com essa matricula: "+ id);

            }
            return alunoRepository.save(aluno);
        } catch (NoSuchElementException e) {
            throw new  NotFoundException ("Não foi encontrado nenhum Aluno com essa matricula: "+ id);
        }

    }

    public Aluno findById(Long id) throws NotFoundException {
        return alunoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum Aluno com essa matricula!"));
    }

    public void apagarAlunos(Long id) throws NotFoundException{
        try {
            alunoRepository.delete(alunoRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            throw new  NotFoundException ("Não foi encontrado nenhum Aluno com essa matricula: "+ id);
        }
    }


}
