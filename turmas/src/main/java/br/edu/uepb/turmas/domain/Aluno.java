package br.edu.uepb.turmas.domain;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

@Table(name = "alunos", uniqueConstraints= {
    @UniqueConstraint(columnNames ={"email","IdMatricula"})
})
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long IdMatricula;

    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMatriculaAluno",referencedColumnName = "idMatricula")
    private List<Turma> turma;
}
