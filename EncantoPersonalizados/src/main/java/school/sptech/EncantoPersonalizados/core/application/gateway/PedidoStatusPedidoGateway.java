package school.sptech.EncantoPersonalizados.core.application.gateway;

import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;

import java.util.List;
import java.util.Optional;

public interface PedidoStatusPedidoGateway {
    PedidoStatusPedido salvar(PedidoStatusPedido entity);
    Optional<PedidoStatusPedido> findById(Integer id);
    List<PedidoStatusPedido> findStatusAtualByPedidoId(Integer pedidoId);
}
