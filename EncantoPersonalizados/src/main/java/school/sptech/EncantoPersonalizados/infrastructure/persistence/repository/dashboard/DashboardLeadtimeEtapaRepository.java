package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeEtapa;

import java.util.List;

public interface DashboardLeadtimeEtapaRepository extends JpaRepository<DashboardLeadtimeEtapa, String> {

    @Query(value = """
            SELECT sp.status AS etapa,
                   AVG(DATEDIFF(psp.updated_at, psp.created_at)) AS lead_time
            FROM pedido p
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
            JOIN status_pedido sp ON sp.id = psp.status_id
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE p.ativo = 1
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
            GROUP BY sp.status
            ORDER BY lead_time DESC
            """, nativeQuery = true)
    List<DashboardLeadtimeEtapa> findAllFiltered(
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
