package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardDespesas;

import java.time.LocalDate;
import java.util.List;

public interface DashboardDespesasRepository extends JpaRepository<DashboardDespesas, Long> {

    @Query("SELECT new school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.CategoriaDTO(d.nomeCategoria, SUM(d.valorTotal)) " +
            "FROM DashboardDespesas d " +
            "WHERE d.dataReferencia BETWEEN :inicio AND :fim " +
            "GROUP BY d.nomeCategoria " +
            "ORDER BY SUM(d.valorTotal) DESC")
    List<CategoriaDTO> findTotaisPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
