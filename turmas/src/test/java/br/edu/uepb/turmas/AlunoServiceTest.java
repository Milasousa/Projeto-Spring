package br.edu.uepb.turmas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.uepb.turmas.repository.AlunoRepository;
import br.edu.uepb.turmas.services.AlunoService;

@SpringBootTest
public class AlunoServiceTest {

    public class CoffeeServiceTest {

        @Mock
        private AlunoRepository alunoRepository;
    
        @InjectMocks
        private AlunoService alunoService;
    
        @BeforeEach
        public void initMocks() {
            MockitoAnnotations.initMocks(this);
        }
    
        @AfterEach
        void tearDown() {
        }
      
}
}
