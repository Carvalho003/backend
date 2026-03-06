package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardKpiAPagarTest {

    @Test
    @DisplayName("Entities - DashboardKpiAPagar - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardKpiAPagar d = new DashboardKpiAPagar();
        assertNull(d.getValor());
        assertNull(d.getDataVencimento());
    }
}
