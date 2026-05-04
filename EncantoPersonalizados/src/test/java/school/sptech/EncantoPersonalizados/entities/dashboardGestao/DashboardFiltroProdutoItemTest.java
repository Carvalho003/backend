package school.sptech.EncantoPersonalizados.entities.dashboardGestao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardFiltroProdutoItem;

import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardFiltroProdutoItemTest {

    @Test
    @DisplayName("Entities - DashboardFiltroProdutoItem - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardFiltroProdutoItem d = new DashboardFiltroProdutoItem();
        assertNull(d.getId());
        assertNull(d.getProdutoId());
        assertNull(d.getQtdProd());
    }
}
