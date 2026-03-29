package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardVendas;

import java.time.LocalDate;
import java.util.List;

public interface DashboardVendasRepository extends JpaRepository<DashboardVendas, Long> {

    @Query("SELECT new school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.CategoriaDTO(v.nomeCategoria, SUM(v.valorTotalVendido)) " +
            "FROM DashboardVendas v " +
            "WHERE v.dataReferencia BETWEEN :inicio AND :fim " +
            "GROUP BY v.nomeCategoria " +
            "ORDER BY SUM(v.valorTotalVendido) DESC")
    List<CategoriaDTO> findTotaisPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}