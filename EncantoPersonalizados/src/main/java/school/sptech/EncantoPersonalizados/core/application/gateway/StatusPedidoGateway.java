package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;

import java.util.List;
import java.util.Optional;

public interface StatusPedidoGateway {
    StatusPedido save(StatusPedido statusPedido);
    Optional<StatusPedido> findById(Integer id);
    Page<StatusPedido> filtrar(Boolean ativo, Pageable pageable);
    Optional<StatusPedido> findFirstByOrderByOrdemKanbanAsc();
    List<StatusPedido> findAll();
}
