package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeEtapa;

import java.util.List;

public interface DashboardLeadtimeEtapaRepository extends JpaRepository<DashboardLeadtimeEtapa, String> {

    @Query("SELECT d FROM DashboardLeadtimeEtapa d ORDER BY d.leadTime DESC")
    List<DashboardLeadtimeEtapa> findAllOrderByLeadTimeDesc();
}
