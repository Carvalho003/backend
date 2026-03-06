package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.entities.Contraparte;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class ContraparteRepositoryTest {

    @Autowired
    private ContraparteRepository repo;

    @Test
    @DisplayName("Repository - Contraparte - findByIdAndStatusTrue retorna presente após salvar")
    void findByIdAndStatusTrue_afterSave() {
        Contraparte c = new Contraparte();
        c.setNome("Teste");
        c.setDescricao("Desc");
        c.setSegmento("Seg");
        c.setTipoContrato("Tipo");
        c.setStatus(true);
        c = repo.save(c);

        assertTrue(repo.findByIdAndStatusTrue(c.getId()).isPresent());
    }
}

