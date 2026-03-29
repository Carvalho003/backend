package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.EnderecoClienteRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class EnderecoClienteRepositoryTest {

    @Autowired
    private EnderecoClienteRepository repo;

    @Test
    @DisplayName("Repository - EnderecoCliente - deve salvar e recuperar endereço")
    void saveAndFind_shouldWork() {
        EnderecoCliente e = new EnderecoCliente();
        e.setLogradouro("Rua Teste");
        e.setCidade("Cidade");
        e = repo.save(e);

        assertNotNull(e.getId());
        assertTrue(repo.findById(e.getId()).isPresent());
    }
}

