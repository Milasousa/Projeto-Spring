package br.edu.uepb.turmas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.uepb.turmas.domain.Aluno;
import br.edu.uepb.turmas.domain.IntegranteENUM;
import br.edu.uepb.turmas.domain.Projeto;
import br.edu.uepb.turmas.domain.User;
import lombok.Data;

@Data
public class AlunoDTO {
    private Long id;
    private String nome;
    private String email;
    private IntegranteENUM funcao;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
