package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import school.sptech.EncantoPersonalizados.core.domain.Produto;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ProdutoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repo;

    @Test
    @DisplayName("Repository - ProdutoRepository - deve salvar e filtrar produto por titulo")
    void saveAndFilter_shouldWork() {
        Produto p = new Produto();
        p.setTitulo("Produto Teste");
        p.setDescricao("Descricao");
        p = repo.save(p);

        assertNotNull(p.getId());

        var page = repo.filtrar("produto", null, null, null, true, PageRequest.of(0, 10));
        // o filtro exige ativo boolean — passamos true; produto salvo usa valor default true
        assertFalse(page.isEmpty());
    }
}

