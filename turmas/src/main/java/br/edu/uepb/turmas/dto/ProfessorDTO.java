package br.edu.uepb.turmas.dto;

import lombok.Data;

@Data
public class ProfessorDTO {
    private Long IdMatricula;
    private String nome;
    private String formacao;
    private String email;
}
