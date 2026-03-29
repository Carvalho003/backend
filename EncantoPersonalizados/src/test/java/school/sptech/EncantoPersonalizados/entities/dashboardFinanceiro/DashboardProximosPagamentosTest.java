package school.sptech.EncantoPersonalizados.core.domain.dashboardFinanceiro;

import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardProximosPagamentos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardProximosPagamentosTest {

    @Test
    @DisplayName("Entities - DashboardProximosPagamentos - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardProximosPagamentos d = new DashboardProximosPagamentos();
        assertNull(d.getDescricao());
        assertNull(d.getValor());
        assertNull(d.getDataVencimento());
    }
}
