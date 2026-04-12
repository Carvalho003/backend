package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeMensal;

import java.util.List;

public interface DashboardLeadtimeMensalRepository extends JpaRepository<DashboardLeadtimeMensal, String> {

    @Query("SELECT d FROM DashboardLeadtimeMensal d ORDER BY d.mes DESC")
    List<DashboardLeadtimeMensal> findAllOrderByMesDesc();
}
