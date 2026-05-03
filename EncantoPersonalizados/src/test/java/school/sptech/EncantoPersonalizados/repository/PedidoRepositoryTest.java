package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.PedidoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repo;

    @Test
    @DisplayName("Repository - PedidoRepository - deve salvar e filtrar pedidos")
    void saveAndFilter_shouldWork() {
        Pedido p = new Pedido();
        p.setObservacoes("Obs");
        p.setOrigem("Obs");
        p = repo.save(p);

        assertNotNull(p.getId());
        assertTrue(repo.findByIdWithDetails(p.getId()).isPresent());

        var page = repo.filtrar("obs", true, null, null, PageRequest.of(0, 10));
        assertFalse(page.isEmpty());
    }
}

