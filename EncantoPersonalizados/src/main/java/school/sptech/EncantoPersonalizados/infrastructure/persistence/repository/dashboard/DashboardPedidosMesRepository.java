package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidosMes;

import java.util.List;

public interface DashboardPedidosMesRepository extends JpaRepository<DashboardPedidosMes, String> {

    @Query(value = """
            SELECT DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
                   COUNT(p.id) AS total_criados,
                   SUM(CASE WHEN sp.status = 'Entregue' AND psp.status_atual = 1 THEN 1 ELSE 0 END) AS total_entregues
            FROM pedido p
            LEFT JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            LEFT JOIN status_pedido sp ON sp.id = psp.status_id
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE p.ativo = 1
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
            GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
            ORDER BY mes
            """, nativeQuery = true)
    List<DashboardPedidosMes> findAllFiltered(
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
