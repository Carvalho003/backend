package school.sptech.EncantoPersonalizados.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import school.sptech.EncantoPersonalizados.entities.Usuario;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repo;

    @Test
    @DisplayName("Repository - Usuario - deve salvar e recuperar usuario")
    void saveAndFind_shouldWork() {
        Usuario u = new Usuario();
        u.setName("User Test");
        u.setEmail("user@test.com");
        u.setPassword("pwd");
        u = repo.save(u);

        assertNotNull(u.getId());
        assertTrue(repo.findById(u.getId()).isPresent());
    }
}

