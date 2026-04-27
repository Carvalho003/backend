package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;

import java.util.List;

public interface DashboardRetrabalhoQuantidadeMesRepository extends JpaRepository<DashboardRetrabalhoQuantidadeMes, String> {

    @Query(value = """
            SELECT DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
                   COUNT(*) AS quantidade_pedidos
            FROM pedido p
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
            LEFT JOIN produto prod ON prod.id = pp.produto_id
            LEFT JOIN tema_produto t ON t.id = prod.tema_produto_id
            WHERE tp.tipo_pedido = 'Retrabalho' AND p.ativo = 1
              AND (:produtoId IS NULL OR pp.produto_id = :produtoId)
              AND (:temaId IS NULL OR t.id = :temaId)
            GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
            ORDER BY mes ASC
            """, nativeQuery = true)
    List<DashboardRetrabalhoQuantidadeMes> findAllFiltered(
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}
