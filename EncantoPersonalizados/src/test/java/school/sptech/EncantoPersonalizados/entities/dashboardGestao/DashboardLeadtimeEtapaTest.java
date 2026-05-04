package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeEtapa;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardLeadtimeEtapaTest {

    @Test
    @DisplayName("Entities - DashboardLeadtimeEtapa - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardLeadtimeEtapa d = new DashboardLeadtimeEtapa();
        assertNull(d.getEtapa());
        assertNull(d.getLeadTime());
    }
}
