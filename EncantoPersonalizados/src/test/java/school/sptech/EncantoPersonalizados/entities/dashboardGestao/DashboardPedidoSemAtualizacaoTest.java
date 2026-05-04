package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidoSemAtualizacao;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardPedidoSemAtualizacaoTest {

    @Test
    @DisplayName("Entities - DashboardPedidoSemAtualizacao - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardPedidoSemAtualizacao d = new DashboardPedidoSemAtualizacao();
        assertNull(d.getId());
        assertNull(d.getCliente());
        assertNull(d.getStatus());
        assertNull(d.getDiasParado());
        assertNull(d.getResponsavel());
    }
}
