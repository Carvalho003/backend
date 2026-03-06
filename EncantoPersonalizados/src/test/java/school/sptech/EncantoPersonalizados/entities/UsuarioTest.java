package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Entities - Usuario - deve setar name, email e password")
    void shouldSetNameEmailAndPassword() {
        Usuario u = new Usuario();
        u.setName("Joao");
        u.setEmail("joao@test.com");
        u.setPassword("senha");

        assertEquals("Joao", u.getName());
        assertEquals("joao@test.com", u.getEmail());
        assertEquals("senha", u.getPassword());
    }
}

