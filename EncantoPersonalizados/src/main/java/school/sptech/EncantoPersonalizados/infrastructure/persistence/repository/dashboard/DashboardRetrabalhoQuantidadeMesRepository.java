package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRetrabalhoQuantidadeMesRepository extends JpaRepository<DashboardRetrabalhoQuantidadeMes, String> {

    @Query(value = """
            SELECT DATE_FORMAT(psp.created_at, '%Y-%m') AS mes,
                   COUNT(*) AS quantidade_pedidos
            FROM pedido p
            JOIN vw_tipo_pedido tp ON tp.id = p.id
            JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
            WHERE tp.tipo_pedido = 'Retrabalho' AND p.ativo = 1
              AND (:produtoId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId
              ))
              AND (:temaId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp2
                  JOIN produto prod2 ON prod2.id = pp2.produto_id
                  WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId
              ))
              AND DATE(psp.created_at) BETWEEN :inicio AND :fim
            GROUP BY DATE_FORMAT(psp.created_at, '%Y-%m')
            ORDER BY mes ASC
            """, nativeQuery = true)
    List<DashboardRetrabalhoQuantidadeMes> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}