package br.edu.uepb.turmas.dto;

import lombok.Data;

@Data
public class ProfessorDTO {
    private Long id;
    private String nome;
    private String formacao;
    private String email;
}
