package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaMovimentacaoTest {

    @Test
    @DisplayName("Entities - CategoriaMovimentacao - deve setar descricao e status")
    void shouldSetDescricaoAndStatus() {
        CategoriaMovimentacao c = new CategoriaMovimentacao();
        c.setDescricao("Receita");
        c.setStatus(true);

        assertEquals("Receita", c.getDescricao());
        assertTrue(c.getStatus());
    }
}

