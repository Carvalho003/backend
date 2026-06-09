package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidosMes;

import java.time.LocalDate;
import java.util.List;

public interface DashboardPedidosMesRepository extends JpaRepository<DashboardPedidosMes, String> {

    @Query(value = """
            SELECT DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
                   COUNT(DISTINCT p.id) AS total_criados,
                   COUNT(DISTINCT CASE
                       WHEN EXISTS (
                           SELECT 1
                           FROM pedido_status_pedido psp
                           JOIN status_pedido sp ON sp.id = psp.status_id
                           WHERE psp.pedido_id = p.id
                             AND sp.status_role IN ('ENTREGUE', 'FINALIZADO')
                       ) THEN p.id
                   END) AS total_entregues
            FROM pedido p
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
            WHERE p.ativo = 1
              AND DATE(p.created_at) BETWEEN :inicio AND :fim
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId))
              AND (:temaId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp2 JOIN produto prod2 ON prod2.id = pp2.produto_id WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId))
            GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
            ORDER BY mes ASC
            """, nativeQuery = true)
    List<DashboardPedidosMes> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
