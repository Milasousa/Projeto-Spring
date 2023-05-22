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
    @PostMapping
    public ResponseEntity<?> criarProjeto(@RequestBody ProjetoDTO  projetoDTO) {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            return new ResponseEntity<>(projetoService.criarProjeto(projeto), HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTurma(@PathVariable("id") Long id, @RequestBody ProjetoDTO  projetoDTO) throws NotFoundException {
        try {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            return new ResponseEntity<>(projetoMapper.convertToProjetoDTO(projetoService.atualizarProjeto(id,projeto)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));

        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> apagarTurma(@PathVariable ("id")  Long id) {
        try {
            projetoService.apagarProjeto(id);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);

        } catch (NotFoundException e) {
            return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));

        }
    }

    @PostMapping("/{professorId}")
    public ResponseEntity<?> vincularProjetoProfResp(@RequestBody ProjetoDTO  projetoDTO,@PathVariable("professorId") Long professorId) throws NotFoundException {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            try {
                return new ResponseEntity<>(projetoService.vincularProjetoProfResp(projeto, professorId), HttpStatus.CREATED);
    
            } catch (NotFoundException e) {
                return ((BodyBuilder) ResponseEntity.notFound()).body(new ErroRespostaGenericaDTO(e.getMessage()));
    
            }
    }
}
