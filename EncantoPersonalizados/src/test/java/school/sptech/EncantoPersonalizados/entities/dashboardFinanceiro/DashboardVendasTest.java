package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardVendasTest {

    @Test
    @DisplayName("Entities - DashboardVendas - getters retornam null por padrão")
    void gettersShouldBeNullByDefault() {
        DashboardVendas d = new DashboardVendas();
        assertNull(d.getNomeCategoria());
        assertNull(d.getQuantidadePedidos());
        assertNull(d.getValorTotalVendido());
    }
}
