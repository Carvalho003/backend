package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;

import static org.junit.jupiter.api.Assertions.*;

class PedidoStatusPedidoTest {

    @Test
    @DisplayName("Entities - PedidoStatusPedido - deve setar statusAtual e relacionamentos")
    void shouldSetStatusAndRelations() {
        PedidoStatusPedido psp = new PedidoStatusPedido();
        psp.setStatusAtual(true);

        StatusPedido sp = new StatusPedido();
        sp.setStatus("Em Andamento");
        psp.setStatus(sp);

        Pedido p = new Pedido();
        psp.setPedido(p);

        assertTrue(psp.isStatusAtual());
        assertEquals(sp, psp.getStatus());
        assertEquals(p, psp.getPedido());
    }
}

