package school.sptech.EncantoPersonalizados.core.application.usecase.statusPedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido.StatusPedidoReordenacaoDto;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeConflitoException; // Importe a sua exception

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
        String nomeTratado = statusPedido.getStatus().trim();
        statusPedido.setStatus(nomeTratado);

        if (gateway.existsByStatusIgnoreCaseAndAtivoTrue(nomeTratado)) {
            throw new EntidadeConflitoException("Já existe um status ativo com o nome: " + nomeTratado);
        }

        statusPedido.setCreatedAt(LocalDateTime.now());
        statusPedido.setAtivo(true);
        return gateway.save(statusPedido);
    }

    @Override
    public StatusPedido update(StatusPedido statusPedido, Integer id) {
        StatusPedido existente = gateway.findById(id).orElseThrow();

        String nomeTratado = statusPedido.getStatus().trim();

        if (!existente.getStatus().equalsIgnoreCase(nomeTratado) &&
                gateway.existsByStatusIgnoreCaseAndAtivoTrue(nomeTratado)) {
            throw new EntidadeConflitoException("Já existe um status ativo com o nome: " + nomeTratado);
        }

        existente.setStatus(nomeTratado);
        existente.setCor(statusPedido.getCor());
        existente.setOrdemKanban(statusPedido.getOrdemKanban());
        existente.setUpdatedAt(LocalDateTime.now());
        return gateway.save(existente);
    }

    @Override
    public void mudarEstado(Integer id) {
        StatusPedido existente = gateway.findById(id).orElseThrow();

        if (existente.isAtivo()) {
            if (gateway.existePedidoVinculado(id)) {
                throw new EntidadeConflitoException("Não é possível remover este status pois existem pedidos vinculados a ele.");
            }
        }

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