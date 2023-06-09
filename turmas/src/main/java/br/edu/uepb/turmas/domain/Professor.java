package br.edu.uepb.turmas.domain;

import java.util.List;
//import java.util.Optional;
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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "professores", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email", "idMatricula" })
})
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatricula;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "formacao", nullable = false)
    private String formacao;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMatriculaProfessor", referencedColumnName = "idMatricula")
    private List<Turma> turma;

}
