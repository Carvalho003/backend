package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardKpi;

import java.time.LocalDate;
import java.util.List;

public interface DashboardKpiRepository extends JpaRepository<DashboardKpi, Long> {
    @Query(value = "SELECT * FROM V_DASH_KPI_MES WHERE mes_referencia BETWEEN :dataInicial AND :dataFinal",
            nativeQuery = true)
    List<DashboardKpi> findKpiAndEvolutionData(
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal
    );
}
