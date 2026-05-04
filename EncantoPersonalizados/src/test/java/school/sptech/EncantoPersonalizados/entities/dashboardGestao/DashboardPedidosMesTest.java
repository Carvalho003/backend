package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidosMes;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardPedidosMesTest {

    @Test
    @DisplayName("Entities - DashboardPedidosMes - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardPedidosMes d = new DashboardPedidosMes();
        assertNull(d.getMes());
        assertNull(d.getTotalCriados());
        assertNull(d.getTotalEntregues());
    }
}
