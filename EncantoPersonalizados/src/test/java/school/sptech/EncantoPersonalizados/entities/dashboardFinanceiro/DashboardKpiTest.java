package school.sptech.EncantoPersonalizados.core.domain.dashboardFinanceiro;

import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardKpi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardKpiTest {

    @Test
    @DisplayName("Entities - DashboardKpi - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardKpi d = new DashboardKpi();
        assertNull(d.getTipoMovimentacao());
        assertNull(d.getValorMesAtual());
        assertNull(d.getValorMesAnterior());
        assertNull(d.getPercentualVariacao());
        assertNull(d.getMesReferencia());
    }
}

