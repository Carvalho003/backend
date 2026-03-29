package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoPedidoTest {

    @Test
    @DisplayName("Entities - ProdutoPedido - deve setar qtd e preco unitario")
    void shouldSetQtdAndPrecoUnitario() {
        ProdutoPedido pp = new ProdutoPedido();
        pp.setQtdProduto(3);
        pp.setPrecoUnitario(12.5);

        assertEquals(3, pp.getQtdProduto());
        assertEquals(12.5, pp.getPrecoUnitario());
    }
}

