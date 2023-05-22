package br.edu.uepb.turmas.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uepb.turmas.domain.Professor;
import br.edu.uepb.turmas.domain.Projeto;
import br.edu.uepb.turmas.repository.ProfessorRepository;
import br.edu.uepb.turmas.repository.ProjetoRepository;
import javassist.NotFoundException;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ProfessorRepository professorrepository;
        public List<Projeto> listAllProjetos() {
            return projetoRepository.findAll();
        }
        public Projeto criarProjeto(Projeto projeto) {
            return projetoRepository.save(projeto);
            
        }
        public Projeto vincularProjetoProfResp(Projeto projeto,Long ProfessorId) throws NotFoundException {
            try {
                Professor professor = professorrepository.findById(ProfessorId).get();
                projeto.setProfessor(professor);
                return projetoRepository.save(projeto);
            } catch (NoSuchElementException e) {
                throw new NotFoundException("Não foi encontrado, o identificador informado");
    
            }
    
        }   
        public Projeto atualizarProjeto(Long id, Projeto projeto) throws NotFoundException {
            try {
                if ((projetoRepository.findById(id).get()) == null) {
                    throw new NotFoundException("Não existe nenhuma Projeto com esse identificador: " + id);
    
                }
                return projetoRepository.save(projeto);
            } catch (NoSuchElementException e) {
                throw new NotFoundException("Não existe nenhum Projeto com esse identificador: " + id);
    
            }
        }
    
        public void apagarProjeto(Long id) throws NotFoundException {
            try {
                projetoRepository.delete(projetoRepository.findById(id).get());
            } catch (NoSuchElementException e) {
                throw new NotFoundException("Não existe nenhum Projeto com esse identificador: " + id);
    
            }
        }
        public Projeto findById(Long id) throws NotFoundException {
            return projetoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum projeto com esse Id!"));
        }
}
