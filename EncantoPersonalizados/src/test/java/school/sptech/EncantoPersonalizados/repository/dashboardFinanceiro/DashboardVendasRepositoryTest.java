package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class DashboardVendasRepositoryTest {

    @Autowired
    private DashboardVendasRepository repo;

    @Test
    @DisplayName("Repository - DashboardVendas - bean é injetado")
    void repoBean_shouldBeInjected() {
        assertNotNull(repo);
    }
}

