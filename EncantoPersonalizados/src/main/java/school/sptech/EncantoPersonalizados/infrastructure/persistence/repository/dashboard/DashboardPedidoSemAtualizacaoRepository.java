package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardPedidoSemAtualizacao;

import java.time.LocalDate;
import java.util.List;

public interface DashboardPedidoSemAtualizacaoRepository extends JpaRepository<DashboardPedidoSemAtualizacao, Long> {

    @Query(value = """
            SELECT p.id,
                   c.nome AS cliente,
                   sp.status,
                   DATEDIFF(NOW(), psp.created_at) AS dias_parado,
                   u.name AS responsavel
            FROM pedido p
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            JOIN status_pedido sp ON sp.id = psp.status_id
            JOIN cliente c ON c.id = p.cliente_id
            JOIN usuario u ON u.id = p.usuario_id
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
            WHERE p.ativo = 1
              AND sp.status NOT IN ('Entregue', 'Cancelado', 'Finalizado')
              AND DATEDIFF(NOW(), psp.created_at) >= 3
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
            ORDER BY dias_parado DESC
            """, nativeQuery = true)
    List<DashboardPedidoSemAtualizacao> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
