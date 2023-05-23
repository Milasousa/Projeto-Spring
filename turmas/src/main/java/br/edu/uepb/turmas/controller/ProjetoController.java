package br.edu.uepb.turmas.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.turmas.domain.Projeto;
import br.edu.uepb.turmas.dto.ErroRespostaGenericaDTO;
import br.edu.uepb.turmas.dto.ProjetoDTO;
import br.edu.uepb.turmas.mapper.ProjetoMapper;
import br.edu.uepb.turmas.services.ProjetoService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoMapper projetoMapper;

    @Autowired
    private ProjetoService projetoService;


    @GetMapping
    public List<ProjetoDTO> getProjetos() {
        List<Projeto> projeto = projetoService.listAllProjetos();
        return projeto.stream()
                .map(projetoMapper::convertToProjetoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjetosById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(projetoMapper.convertToProjetoDTO(projetoService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new ErroRespostaGenericaDTO(e.getMessage()));
        }
    }
    @GetMapping("alunos/{projetoId}/{alunoId}")
    public  List<ProjetoDTO> listAllAlunos(@PathVariable("projetoId") Long projetoId,@PathVariable("alunoId") Long alunoId) throws NotFoundException {
      // try {
            //return new ResponseEntity<>(projetoMapper.convertToProjetoDTO(projetoService.listAllAlunos(projetoId,alunoId)), HttpStatus.OK);
            List<Projeto> projeto = projetoService.listAllAlunos(projetoId,alunoId);
            return projeto.stream()
            .map(projetoMapper::convertToProjetoDTO)
            .collect(Collectors.toList());
        //} catch (NotFoundException e) {
           // return ResponseEntity.badRequest().body(new ErroRespostaGenericaDTO(e.getMessage()));
        //}
    }
    @PostMapping("/{professorId}")
    public ResponseEntity<?> criarProjeto(@RequestBody ProjetoDTO  projetoDTO,@PathVariable("professorId") Long professorId) throws NotFoundException {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            try {
                return new ResponseEntity<>(projetoService.criarProjeto(projeto, professorId), HttpStatus.CREATED);
    
            } catch (NotFoundException e) {
                return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));
    
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProjeto(@PathVariable("id") Long id, @RequestBody ProjetoDTO  projetoDTO) throws NotFoundException {
        try {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            return new ResponseEntity<>(projetoMapper.convertToProjetoDTO(projetoService.atualizarProjeto(id,projeto)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));

        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> apagarProjeto(@PathVariable ("id")  Long id) {
        try {
            projetoService.apagarProjeto(id);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);

        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));

        }
    }

}
