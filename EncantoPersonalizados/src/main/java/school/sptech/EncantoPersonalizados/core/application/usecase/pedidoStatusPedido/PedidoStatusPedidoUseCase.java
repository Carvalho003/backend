package school.sptech.EncantoPersonalizados.core.application.usecase.pedidoStatusPedido;

import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;

public interface PedidoStatusPedidoUseCase {
    PedidoStatusPedido salvar(PedidoStatusPedido entity);
    PedidoStatusPedido findById(Integer id);
}
