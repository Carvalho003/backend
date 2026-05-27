package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeMensal;

import java.time.LocalDate;
import java.util.List;

public interface DashboardLeadtimeMensalRepository extends JpaRepository<DashboardLeadtimeMensal, String> {

    @Query(value = """
            SELECT DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
                   AVG(DATEDIFF(psp.created_at, p.created_at)) AS lead_time
            FROM pedido p
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
            JOIN status_pedido sp ON sp.id = psp.status_id
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            WHERE psp.status_atual = 1 AND p.ativo = 1 AND sp.status_role = 'FINALIZADO'
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId
              ))
              AND (:temaId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp2
                  JOIN produto prod2 ON prod2.id = pp2.produto_id
                  WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId
              ))
              AND DATE(p.created_at) BETWEEN :inicio AND :fim
            GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
            ORDER BY mes ASC
            """, nativeQuery = true)
    List<DashboardLeadtimeMensal> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
