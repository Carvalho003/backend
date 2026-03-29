package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboardFinanceiro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardDespesasRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class DashboardDespesasRepositoryTest {

    @Autowired
    private DashboardDespesasRepository repo;

    @Test
    @DisplayName("Repository - DashboardDespesas - bean é injetado")
    void repoBean_shouldBeInjected() {
        assertNotNull(repo);
    }
}

