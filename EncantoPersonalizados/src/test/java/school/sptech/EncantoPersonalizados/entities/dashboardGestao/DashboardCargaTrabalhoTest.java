package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardCargaTrabalho;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardCargaTrabalhoTest {

    @Test
    @DisplayName("Entities - DashboardCargaTrabalho - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardCargaTrabalho d = new DashboardCargaTrabalho();
        assertNull(d.getFuncionario());
        assertNull(d.getEmAndamento());
    }
}
