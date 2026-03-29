package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FotoProdutoTest {

    @Test
    @DisplayName("Entities - FotoProduto - deve setar caminho da foto e produto")
    void shouldSetFotoAndProduto() {
        FotoProduto f = new FotoProduto();
        f.setFoto("/img/test.jpg");

        assertEquals("/img/test.jpg", f.getFoto());
        assertNull(f.getProduto());
    }
}

