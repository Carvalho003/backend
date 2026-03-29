package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.CategoriaTemaRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.TemaProdutoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class TemaProdutoRepositoryTest {

    @Autowired
    private TemaProdutoRepository repo;

    @Autowired
    private CategoriaTemaRepository categoriaTemaRepository;

    @Test
    @DisplayName("Repository - TemaProduto - deve salvar e filtrar tema por descricao")
    void saveAndFilter_shouldWork() {
        CategoriaTema cat = new CategoriaTema();
        cat.setTitulo("Categoria Teste");
        cat = categoriaTemaRepository.save(cat);

        TemaProduto t = new TemaProduto();
        t.setDescricao("Tema Teste");
        t.setCategoriaTema(cat);
        t = repo.save(t);

        assertNotNull(t.getId());

        var page = repo.filtrar("tema", null, true, PageRequest.of(0, 10));
        assertFalse(page.isEmpty());
    }
}
