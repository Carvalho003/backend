package school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardDespesas;

import java.time.LocalDate;
import java.util.List;

public interface DashboardDespesasRepository extends JpaRepository<DashboardDespesas, Long> {

    @Query("SELECT new school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.CategoriaDTO(d.nomeCategoria, SUM(d.valorTotal)) " +
            "FROM DashboardDespesas d " +
            "WHERE d.dataReferencia BETWEEN :inicio AND :fim " +
            "GROUP BY d.nomeCategoria " +
            "ORDER BY SUM(d.valorTotal) DESC")
    List<CategoriaDTO> findTotaisPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
