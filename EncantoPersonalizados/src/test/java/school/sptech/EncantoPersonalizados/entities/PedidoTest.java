package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    @DisplayName("Entities - Pedido - deve setar observacoes, origem e checar createdAt default")
    void shouldSetObservacoesAndOrigemAndCreatedAt() {
        Pedido p = new Pedido();
        p.setObservacoes("Observação");
        p.setOrigem("loja");
        p.setAtivo(true);

        assertEquals("Observação", p.getObservacoes());
        assertEquals("loja", p.getOrigem());
        assertTrue(p.isAtivo());
        assertNotNull(p.getCreatedAt());
    }
}
