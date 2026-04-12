package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;

import java.util.List;

public interface DashboardRetrabalhoQuantidadeMesRepository extends JpaRepository<DashboardRetrabalhoQuantidadeMes, String> {

    @Query("SELECT d FROM DashboardRetrabalhoQuantidadeMes d ORDER BY d.mes DESC")
    List<DashboardRetrabalhoQuantidadeMes> findAllOrderByMesDesc();
}
