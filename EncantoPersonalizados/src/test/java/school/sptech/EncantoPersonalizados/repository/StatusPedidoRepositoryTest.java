package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.StatusPedidoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class StatusPedidoRepositoryTest {

    @Autowired
    private StatusPedidoRepository repo;

    @Test
    @DisplayName("Repository - StatusPedido - deve salvar e recuperar status")
    void saveAndFind_shouldWork() {
        StatusPedido s = new StatusPedido();
        s.setStatus("Novo");
        s = repo.save(s);

        assertNotNull(s.getId());
        assertTrue(repo.findById(s.getId()).isPresent());
    }
}

