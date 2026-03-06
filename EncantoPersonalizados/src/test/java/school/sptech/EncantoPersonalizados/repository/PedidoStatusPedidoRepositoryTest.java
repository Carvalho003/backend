package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class PedidoStatusPedidoRepositoryTest {

    @Autowired
    private PedidoStatusPedidoRepository repo;

    @Test
    @DisplayName("Repository - PedidoStatusPedido - deve salvar e recuperar status de pedido")
    void saveAndFind_shouldWork() {
        PedidoStatusPedido ps = new PedidoStatusPedido();
        ps = repo.save(ps);

        assertNotNull(ps.getId());
        assertTrue(repo.findById(ps.getId()).isPresent());
    }
}

