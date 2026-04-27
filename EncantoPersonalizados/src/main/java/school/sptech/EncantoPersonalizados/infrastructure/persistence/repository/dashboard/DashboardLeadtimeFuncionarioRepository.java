package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeFuncionario;

import java.util.List;

public interface DashboardLeadtimeFuncionarioRepository extends JpaRepository<DashboardLeadtimeFuncionario, String> {

    @Query(value = """
            SELECT u.name AS funcionario,
                   AVG(DATEDIFF(psp.created_at, p.created_at)) AS lead_time,
                   COUNT(p.id) AS total_pedidos
            FROM pedido p
            JOIN usuario u ON u.id = p.usuario_id
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
            JOIN status_pedido sp ON sp.id = psp.status_id
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE psp.status_atual = 1 AND p.ativo = 1 AND sp.status = 'Finalizado'
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
            GROUP BY u.name
            ORDER BY lead_time DESC
            """, nativeQuery = true)
    List<DashboardLeadtimeFuncionario> findAllFiltered(
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
