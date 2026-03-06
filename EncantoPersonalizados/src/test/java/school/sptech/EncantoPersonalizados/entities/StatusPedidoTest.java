package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPedidoTest {

    @Test
    @DisplayName("Entities - StatusPedido - deve setar status e verificar ativo default")
    void shouldSetStatusAndDefaultAtivo() {
        StatusPedido s = new StatusPedido();
        s.setStatus("Pendente");

        assertEquals("Pendente", s.getStatus());
        assertTrue(s.isAtivo());
    }
}

