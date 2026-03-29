package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaProdutoTest {

    @Test
    @DisplayName("Entities - TemaProduto - deve setar descricao e verificar ativo default")
    void shouldSetDescricaoAndDefaultAtivo() {
        TemaProduto t = new TemaProduto();
        t.setDescricao("Tema X");

        assertEquals("Tema X", t.getDescricao());
        assertTrue(t.getAtivo());
    }
}

