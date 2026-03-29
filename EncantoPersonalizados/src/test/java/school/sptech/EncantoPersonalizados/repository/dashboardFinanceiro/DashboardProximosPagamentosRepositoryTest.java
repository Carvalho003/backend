package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboardFinanceiro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardProximosPagamentosRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class DashboardProximosPagamentosRepositoryTest {

    @Autowired
    private DashboardProximosPagamentosRepository repo;

    @Test
    @DisplayName("Repository - DashboardProximosPagamentos - bean é injetado")
    void repoBean_shouldBeInjected() {
        assertNotNull(repo);
    }
}

