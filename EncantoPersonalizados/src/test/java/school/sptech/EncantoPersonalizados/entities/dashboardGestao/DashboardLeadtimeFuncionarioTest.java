package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeFuncionario;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardLeadtimeFuncionarioTest {

    @Test
    @DisplayName("Entities - DashboardLeadtimeFuncionario - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardLeadtimeFuncionario d = new DashboardLeadtimeFuncionario();
        assertNull(d.getFuncionario());
        assertNull(d.getLeadTime());
        assertNull(d.getTotalPedidos());
    }
}
