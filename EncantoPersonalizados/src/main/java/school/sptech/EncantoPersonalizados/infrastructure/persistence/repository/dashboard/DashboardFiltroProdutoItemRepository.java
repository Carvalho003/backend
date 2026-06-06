package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardFiltroProdutoItem;

import java.time.LocalDate;
import java.util.List;

public interface DashboardFiltroProdutoItemRepository extends JpaRepository<DashboardFiltroProdutoItem, Long> {

    @Query(value = """
            SELECT p.id AS id,
                   pp.produto_id AS produto_id,
                   COALESCE(prod.item_produto_id, 0) AS qtd_prod
            FROM pedido p
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE p.ativo = 1
              AND (:tipoPedido IS NULL OR tp.tipo_pedido = :tipoPedido)
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
              AND DATE(psp.created_at) BETWEEN :inicio AND :fim
            ORDER BY qtd_prod DESC
            """, nativeQuery = true)
    List<DashboardFiltroProdutoItem> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tipoPedido") String tipoPedido,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}