package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardProximosPagamentos;

import java.time.LocalDate;
import java.util.List;

public interface DashboardProximosPagamentosRepository extends JpaRepository<DashboardProximosPagamentos, Integer> {

    @Query("SELECT d FROM DashboardProximosPagamentos d " +
            "WHERE d.dataVencimento BETWEEN :inicio AND :fim " +
            "ORDER BY d.dataVencimento ASC")
    List<DashboardProximosPagamentos> findProximosPagamentos(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
