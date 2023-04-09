 package br.edu.uepb.turmas.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.uepb.turmas.domain.Aluno;




@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findBynome(String email);


}