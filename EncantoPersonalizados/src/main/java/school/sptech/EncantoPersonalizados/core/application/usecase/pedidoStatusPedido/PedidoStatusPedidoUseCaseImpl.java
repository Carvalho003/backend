package school.sptech.EncantoPersonalizados.core.application.usecase.pedidoStatusPedido;

import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoStatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;

import java.util.List;

@Component
public class PedidoStatusPedidoUseCaseImpl implements PedidoStatusPedidoUseCase {

    private final PedidoStatusPedidoGateway pedidoStatusPedidoGateway;
    private final PedidoGateway pedidoGateway;
    private final StatusPedidoGateway statusPedidoGateway;

    public PedidoStatusPedidoUseCaseImpl(
            PedidoStatusPedidoGateway pedidoStatusPedidoGateway,
            PedidoGateway pedidoGateway,
            StatusPedidoGateway statusPedidoGateway
    ) {
        this.pedidoStatusPedidoGateway = pedidoStatusPedidoGateway;
        this.pedidoGateway = pedidoGateway;
        this.statusPedidoGateway = statusPedidoGateway;
    }

    @Override
    public PedidoStatusPedido salvar(PedidoStatusPedido entity) {
        if (statusPedidoGateway.findById(entity.getStatus().getId()).isEmpty()) {
            throw new RuntimeException("StatusPedido não encontrado");
        }

        Pedido pedido = pedidoGateway.findById(entity.getPedido().getId()).orElse(null);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        List<PedidoStatusPedido> statusAtuais = pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId());
        for (PedidoStatusPedido statusAtual : statusAtuais) {
            statusAtual.setStatusAtual(false);
            pedidoStatusPedidoGateway.salvar(statusAtual);
        }

        return pedidoStatusPedidoGateway.salvar(entity);
    }

    @Override
    public PedidoStatusPedido findById(Integer id) {
        return pedidoStatusPedidoGateway.findById(id).orElse(null);
    }
}
