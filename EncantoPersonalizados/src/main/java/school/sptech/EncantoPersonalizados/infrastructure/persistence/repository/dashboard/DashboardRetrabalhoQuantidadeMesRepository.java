package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRetrabalhoQuantidadeMesRepository extends JpaRepository<DashboardRetrabalhoQuantidadeMes, String> {

    @Query(value = """
            SELECT DATE_FORMAT(psp_rework.created_at, '%Y-%m') AS mes,
                   COUNT(DISTINCT p.id) AS quantidade_pedidos
            FROM pedido p
            JOIN pedido_status_pedido psp_rework ON psp_rework.pedido_id = p.id
            JOIN status_pedido sp_rework ON sp_rework.id = psp_rework.status_id
            WHERE p.ativo = 1
              -- Correção: Trata o valor NULL da etapa "Em Produção"
              AND (sp_rework.status_role IS NULL OR sp_rework.status_role NOT IN ('FINALIZADO', 'ENTREGUE', 'CANCELADO'))
              -- Correção: Utiliza o psp_hist.id para garantir a ordem temporal exata
              AND EXISTS (
                  SELECT 1 FROM pedido_status_pedido psp_hist
                  JOIN status_pedido sp_hist ON sp_hist.id = psp_hist.status_id
                  WHERE psp_hist.pedido_id = p.id
                    AND sp_hist.status_role IN ('FINALIZADO', 'ENTREGUE')
                    AND psp_hist.id < psp_rework.id
              )
              AND (:produtoId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp WHERE pp.pedido_id = p.id AND pp.produto_id = :produtoId
              ))
              AND (:temaId IS NULL OR EXISTS (
                  SELECT 1 FROM produto_pedido pp2
                  JOIN produto prod2 ON prod2.id = pp2.produto_id
                  WHERE pp2.pedido_id = p.id AND prod2.tema_produto_id = :temaId
              ))
              AND DATE(psp_rework.created_at) BETWEEN :inicio AND :fim
            GROUP BY DATE_FORMAT(psp_rework.created_at, '%Y-%m')
            ORDER BY mes ASC
            """, nativeQuery = true)
    List<DashboardRetrabalhoQuantidadeMes> findAllFiltered(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("produtoId") Long produtoId,
            @Param("temaId") Long temaId
    );
}