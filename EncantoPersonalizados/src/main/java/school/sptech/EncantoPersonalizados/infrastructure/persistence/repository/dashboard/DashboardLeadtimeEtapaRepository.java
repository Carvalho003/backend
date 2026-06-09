package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeEtapa;

import java.time.LocalDate;
import java.util.List;

public interface DashboardLeadtimeEtapaRepository extends JpaRepository<DashboardLeadtimeEtapa, String> {

    @Query(value = """
            SELECT sp.status AS etapa,
                   AVG(GREATEST(DATEDIFF(COALESCE(psp.updated_at, psp.created_at), psp.created_at), 0)) AS lead_time
            FROM pedido p
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
            JOIN status_pedido sp ON sp.id = psp.status_id
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            WHERE p.ativo = 1
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId
              ))
              AND (:temaId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp2
                  JOIN produto prod2 ON prod2.id = pp2.produto_id
                  WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId
              ))
              AND DATE(psp.created_at) BETWEEN :inicio AND :fim
            GROUP BY sp.status
            ORDER BY lead_time DESC
            """, nativeQuery = true)
    List<DashboardLeadtimeEtapa> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
