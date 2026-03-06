package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTemaTest {

    @Test
    @DisplayName("Entities - CategoriaTema - deve setar titulo e ter ativo por padrão true")
    void shouldSetTituloAndDefaultAtivoTrue() {
        CategoriaTema c = new CategoriaTema();
        c.setTitulo("Titulo Teste");

        assertEquals("Titulo Teste", c.getTitulo());
        assertTrue(c.getAtivo());
    }
}

