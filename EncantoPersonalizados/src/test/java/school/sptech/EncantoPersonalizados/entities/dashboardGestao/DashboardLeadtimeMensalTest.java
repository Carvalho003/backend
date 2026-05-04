package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeMensal;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardLeadtimeMensalTest {

    @Test
    @DisplayName("Entities - DashboardLeadtimeMensal - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardLeadtimeMensal d = new DashboardLeadtimeMensal();
        assertNull(d.getMes());
        assertNull(d.getLeadTime());
    }
}
