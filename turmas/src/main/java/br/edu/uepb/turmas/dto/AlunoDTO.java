package br.edu.uepb.turmas.dto;
import br.edu.uepb.turmas.domain.IntegranteENUM;
import lombok.Data;

@Data
public class AlunoDTO {
    private Long IdMatricula;
    private String nome;
    private String email;
    private IntegranteENUM papel;

}



