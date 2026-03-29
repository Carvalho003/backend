package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;

import java.util.List;

public interface PedidoStatusPedidoRepository extends JpaRepository<PedidoStatusPedido, Integer> {
    @Query("""
            SELECT psp 
            FROM PedidoStatusPedido psp
            WHERE psp.pedido.id = :pedidoId AND psp.statusAtual = true
            """)
    List<PedidoStatusPedido> findStatusAtualByPedidoId(@Param("pedidoId") Integer pedidoId);

}
