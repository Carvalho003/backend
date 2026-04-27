package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardCargaTrabalho;

import java.util.List;

public interface DashboardCargaTrabalhoRepository extends JpaRepository<DashboardCargaTrabalho, String> {

    @Query(value = """
            SELECT u.name AS funcionario,
                   COUNT(p.id) AS em_andamento
            FROM pedido p
            JOIN usuario u ON u.id = p.usuario_id
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            JOIN status_pedido sp ON sp.id = psp.status_id
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE p.ativo = 1
              AND sp.status NOT IN ('Entregue', 'Cancelado', 'Finalizado')
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
            GROUP BY u.name
            ORDER BY em_andamento DESC
            """, nativeQuery = true)
    List<DashboardCargaTrabalho> findAllFiltered(
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
