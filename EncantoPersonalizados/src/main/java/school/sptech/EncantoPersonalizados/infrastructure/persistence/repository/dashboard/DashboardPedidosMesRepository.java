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
                   COUNT(p.id) AS total_criados,
                   SUM(CASE WHEN sp.status_role = 'ENTREGUE' AND psp.status_atual = 1 THEN 1 ELSE 0 END) AS total_entregues
            FROM pedido p
            LEFT JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            LEFT JOIN status_pedido sp ON sp.id = psp.status_id
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
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
              AND DATE(p.created_at) BETWEEN :inicio AND :fim
            GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
            ORDER BY mes
            """, nativeQuery = true)
    List<DashboardPedidosMes> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
