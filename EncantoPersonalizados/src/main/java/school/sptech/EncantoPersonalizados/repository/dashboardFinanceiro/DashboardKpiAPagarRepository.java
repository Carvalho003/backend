package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardKpiAPagar;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DashboardKpiAPagarRepository extends JpaRepository<DashboardKpiAPagar, Integer> {

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM DashboardKpiAPagar d " +
            "WHERE d.dataVencimento BETWEEN :inicio AND :fim")
    BigDecimal totalAPagarNoPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
