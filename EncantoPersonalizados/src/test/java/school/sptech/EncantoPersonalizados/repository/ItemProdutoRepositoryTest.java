package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ItemProdutoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class ItemProdutoRepositoryTest {

    @Autowired
    private ItemProdutoRepository repo;

    @Test
    @DisplayName("Repository - ItemProduto - deve salvar e recuperar item de produto")
    void saveAndFind_shouldWork() {
        ItemProduto i = new ItemProduto();
        i.setDescricao("Item Teste");
        i.setPrecoVenda(10.0);
        i = repo.save(i);

        assertNotNull(i.getId());
        assertTrue(repo.findById(i.getId()).isPresent());
    }
}
