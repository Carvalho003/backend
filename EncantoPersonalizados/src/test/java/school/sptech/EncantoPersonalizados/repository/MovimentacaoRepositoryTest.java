package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.CategoriaMovimentacaoRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ContraparteRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.MovimentacaoRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class MovimentacaoRepositoryTest {

    @Autowired
    private MovimentacaoRepository repo;

    @Autowired
    private ContraparteRepository contraparteRepository;

    @Autowired
    private CategoriaMovimentacaoRepository categoriaMovimentacaoRepository;

    @Test
    @DisplayName("Repository - Movimentacao - deve salvar movimentacao com contraparte e categoria")
    void saveAndFind_shouldWork() {
        Contraparte c = new Contraparte();
        c.setNome("Contraparte Test");
        c = contraparteRepository.save(c);

        CategoriaMovimentacao cm = new CategoriaMovimentacao();
        cm.setDescricao("CatMov");
        cm.setStatus(true);
        cm = categoriaMovimentacaoRepository.save(cm);

        Movimentacao m = new Movimentacao();
        m.setDescricao("Mov Test");
        m.setValor(123.45);
        m.setContraparte(c);
        m.setCategoriaMovimentacao(cm);
        m = repo.save(m);

        assertNotNull(m.getId());
        assertTrue(repo.findById(m.getId()).isPresent());
    }
}

