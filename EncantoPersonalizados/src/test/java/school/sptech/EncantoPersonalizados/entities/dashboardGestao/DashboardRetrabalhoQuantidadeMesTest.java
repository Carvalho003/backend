package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardRetrabalhoQuantidadeMesTest {

    @Test
    @DisplayName("Entities - DashboardRetrabalhoQuantidadeMes - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardRetrabalhoQuantidadeMes d = new DashboardRetrabalhoQuantidadeMes();
        assertNull(d.getMes());
        assertNull(d.getQuantidadePedidos());
    }
}
