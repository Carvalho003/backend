package school.sptech.EncantoPersonalizados.core.application.usecase.statusPedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido.StatusPedidoReordenacaoDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatusPedidoUseCaseImpl implements StatusPedidoUseCase {

    private final StatusPedidoGateway gateway;

    public StatusPedidoUseCaseImpl(StatusPedidoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Page<StatusPedido> listar(Integer page, Boolean ativo) {
        Pageable pageable = PageRequest.of(page, 10);
        return gateway.filtrar(ativo, pageable);
    }

    @Override
    public StatusPedido store(StatusPedido statusPedido) {
        statusPedido.setCreatedAt(LocalDateTime.now());
        return gateway.save(statusPedido);
    }

    @Override
    public StatusPedido update(StatusPedido statusPedido, Integer id) {
        StatusPedido existente = gateway.findById(id).orElseThrow();
        existente.setStatus(statusPedido.getStatus());
        existente.setCor(statusPedido.getCor());
        existente.setOrdemKanban(statusPedido.getOrdemKanban());
        existente.setUpdatedAt(LocalDateTime.now());
        return gateway.save(existente);
    }

    @Override
    public void mudarEstado(Integer id) {
        StatusPedido existente = gateway.findById(id).orElseThrow();
        existente.setAtivo(!existente.isAtivo());
        gateway.save(existente);
    }

    @Override
    public void reordenarKanban(List<StatusPedidoReordenacaoDto> statusPedidos) {
        for (StatusPedidoReordenacaoDto dto : statusPedidos) {
            StatusPedido existente = gateway.findById(dto.id()).orElseThrow();
            existente.setOrdemKanban(dto.novaOrdemKanban());
            gateway.save(existente);
        }
    }

    @Override
    public StatusPedido findById(Integer id) {
        return gateway.findById(id).orElse(null);
    }

    @Override
    public StatusPedido findFirstOfKanbanOrder() {
        return gateway.findFirstByOrderByOrdemKanbanAsc().orElse(null);
    }
}
