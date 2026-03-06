package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    @DisplayName("Entities - Produto - deve setar titulo, descricao e ativo por padrão true")
    void shouldSetTituloDescricaoAndAtivo() {
        Produto p = new Produto();
        p.setTitulo("Prod");
        p.setDescricao("Desc");

        assertEquals("Prod", p.getTitulo());
        assertEquals("Desc", p.getDescricao());
        assertTrue(p.getAtivo());
    }
}

