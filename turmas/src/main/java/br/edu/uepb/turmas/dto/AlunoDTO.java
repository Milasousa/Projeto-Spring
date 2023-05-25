package br.edu.uepb.turmas.dto;
import br.edu.uepb.turmas.domain.IntegranteENUM;
import br.edu.uepb.turmas.domain.Projeto;
import lombok.Data;

@Data
public class AlunoDTO {
    private Long id;
    private String nome;
    private String email;
    private IntegranteENUM papel;
}



