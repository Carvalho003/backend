package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardKpi;

import java.time.LocalDate;
import java.util.List;

public interface DashboardKpiRepository extends JpaRepository<DashboardKpi, Long> {

    @Query(value = "SELECT * FROM v_dash_kpi_mes WHERE mes_referencia BETWEEN :inicio AND :fim", nativeQuery = true)
    List<DashboardKpi> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
