package br.edu.uepb.turmas.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeDaDisciplina", nullable = false)
    private String nomeDaDisciplina;

    @Column(name = "sala", nullable = false)
    private String sala;
    @ManyToOne
    @JoinColumn(name = "idMatriculaProfessor")
    private Professor prof;

    @ManyToOne
    @JoinColumn(name = "idMatriculaAluno")
    private Aluno aluno;
}
