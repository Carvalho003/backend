package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardTipoPedido;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardTipoPedidoTest {

    @Test
    @DisplayName("Entities - DashboardTipoPedido - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardTipoPedido d = new DashboardTipoPedido();
        assertNull(d.getId());
        assertNull(d.getOrigem());
        assertNull(d.getObservacoes());
        assertNull(d.getStatus());
        assertNull(d.getTipoPedido());
    }
}
