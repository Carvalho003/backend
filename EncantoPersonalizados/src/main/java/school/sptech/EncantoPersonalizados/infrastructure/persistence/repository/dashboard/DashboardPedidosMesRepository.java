package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidosMes;

import java.time.LocalDate;
import java.util.List;

public interface DashboardPedidosMesRepository extends JpaRepository<DashboardPedidosMes, String> {

    @Query(value = """
            SELECT meses.mes,
                   SUM(meses.criado) AS total_criados,
                   SUM(meses.entregue) AS total_entregues
            FROM (
                -- Busca os Criados
                SELECT DATE_FORMAT(p.created_at, '%Y-%m') AS mes, 1 AS criado, 0 AS entregue
                FROM pedido p
                LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
                WHERE p.ativo = 1 AND DATE(p.created_at) BETWEEN :inicio AND :fim
                  AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
                  AND (:produtoId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId))
                  AND (:temaId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp2 JOIN produto prod2 ON prod2.id = pp2.produto_id WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId))
           
                UNION ALL
            
                -- Busca os Entregues/Finalizados
                SELECT DATE_FORMAT(psp.created_at, '%Y-%m') AS mes, 0 AS criado, 1 AS entregue
                FROM pedido_status_pedido psp
                JOIN pedido p ON p.id = psp.pedido_id
                JOIN status_pedido sp ON sp.id = psp.status_id
                LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
                WHERE p.ativo = 1 AND sp.status_role IN ('ENTREGUE', 'FINALIZADO')
                  AND DATE(psp.created_at) BETWEEN :inicio AND :fim
                  AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
                  AND (:produtoId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId))
                  AND (:temaId IS NULL OR EXISTS (SELECT 1 FROM produto_pedido pp2 JOIN produto prod2 ON prod2.id = pp2.produto_id WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId))
            ) AS meses
            GROUP BY meses.mes
            ORDER BY meses.mes ASC
            """, nativeQuery = true)
    List<DashboardPedidosMes> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}