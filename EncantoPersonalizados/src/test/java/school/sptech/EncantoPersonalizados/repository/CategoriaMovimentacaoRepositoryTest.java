package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class CategoriaMovimentacaoRepositoryTest {

    @Autowired
    private CategoriaMovimentacaoRepository repo;

    @Test
    @DisplayName("Repository - CategoriaMovimentacao - existsByDescricao retorna true após salvar")
    void existsByDescricao_shouldReturnTrueAfterSave() {
        CategoriaMovimentacao c = new CategoriaMovimentacao();
        c.setDescricao("TesteDesc");
        c.setStatus(true);
        c = repo.save(c);

        assertTrue(repo.existsByDescricao("TesteDesc"));
        assertTrue(repo.findByIdAndStatusTrue(c.getId()).isPresent());
    }
}

