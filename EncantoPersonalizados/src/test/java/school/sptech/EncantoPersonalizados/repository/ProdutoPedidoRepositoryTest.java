package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ProdutoPedidoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class ProdutoPedidoRepositoryTest {

    @Autowired
    private ProdutoPedidoRepository repo;

    @Test
    @DisplayName("Repository - ProdutoPedido - deve salvar e recuperar produto pedido")
    void saveAndFind_shouldWork() {
        ProdutoPedido pp = new ProdutoPedido();
        pp.setPrecoUnitario(10.0);
        pp.setQtdProduto(2);
        pp = repo.save(pp);

        assertNotNull(pp.getId());
        assertTrue(repo.findById(pp.getId()).isPresent());
    }
}

