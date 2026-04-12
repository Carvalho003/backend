package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeFuncionario;

import java.util.List;

public interface DashboardLeadtimeFuncionarioRepository extends JpaRepository<DashboardLeadtimeFuncionario, String> {

    @Query("SELECT d FROM DashboardLeadtimeFuncionario d ORDER BY d.leadTime DESC")
    List<DashboardLeadtimeFuncionario> findAllOrderByLeadTimeDesc();
}
