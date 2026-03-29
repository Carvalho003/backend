package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;

import java.util.Optional;

public interface PedidoGateway {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Integer id);
    Optional<Pedido> findByIdWithDetails(Integer id);
    Page<Pedido> filtrar(String search, Boolean ativo, Pageable pageable);
}
