package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardTipoPedido;

import java.time.LocalDate;
import java.util.List;

public interface DashboardTipoPedidoRepository extends JpaRepository<DashboardTipoPedido, Long> {

    @Query(value = """
            SELECT tp.id, tp.origem, tp.observacoes, tp.status, tp.status_role, tp.tipo_pedido
            FROM vw_tipo_pedido tp
            JOIN pedido p ON p.id = tp.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = tp.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
              AND DATE(p.created_at) BETWEEN :inicio AND :fim
            GROUP BY tp.id, tp.origem, tp.observacoes, tp.status, tp.status_role, tp.tipo_pedido
            """, nativeQuery = true)
    List<DashboardTipoPedido> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
