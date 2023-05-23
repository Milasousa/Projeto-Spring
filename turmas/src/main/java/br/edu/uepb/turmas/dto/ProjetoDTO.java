package br.edu.uepb.turmas.dto;

import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.domain.Professor;
import lombok.Data;

@Data
public class ProjetoDTO {
    private Long idProjeto;
    private String nome;
    private String descricao;
    private Aluno aluno;
    private Professor professor;
}
