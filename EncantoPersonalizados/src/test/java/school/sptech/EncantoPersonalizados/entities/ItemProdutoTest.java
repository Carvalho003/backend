package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemProdutoTest {

    @Test
    @DisplayName("Entities - ItemProduto - deve setar descricao e precoVenda")
    void shouldSetDescricaoAndPrecoVenda() {
        ItemProduto i = new ItemProduto();
        i.setDescricao("Item X");
        i.setPrecoVenda(50.0);

        assertEquals("Item X", i.getDescricao());
        assertEquals(50.0, i.getPrecoVenda());
        assertTrue(i.getAtivo());
    }
}

