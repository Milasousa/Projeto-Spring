package br.edu.uepb.turmas.repository;

import org.springframework.stereotype.Repository;

import br.edu.uepb.turmas.domain.Projeto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query(value="SELECT A.ID_MATRICULA,A.NOME ,A.PAPEL,P.ID_PROJETO,P.DESCRICAO ,P.NOME  FROM ALUNOS AS A JOIN PROJETOS AS P ON A.ID_PROJETO = P.ID_PROJETO",nativeQuery = true)
    List <Projeto> findAlunosByIdProjetoqueryBy(@Param("ID_PROJETO") Long projetoId,@Param("ID_PROJETO")  Long alunoId);
}
