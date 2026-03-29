package school.sptech.EncantoPersonalizados.core.domain.dashboardFinanceiro;

import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardDespesas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardDespesasTest {

    @Test
    @DisplayName("Entities - DashboardDespesas - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardDespesas d = new DashboardDespesas();
        assertNull(d.getNomeCategoria());
        assertNull(d.getValorTotal());
    }
}

