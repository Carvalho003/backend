package school.sptech.EncantoPersonalizados.core.application.usecase.statusPedido;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido.StatusPedidoReordenacaoDto;

import java.util.List;

public interface StatusPedidoUseCase {
    Page<StatusPedido> listar(Integer page, Boolean ativo);
    StatusPedido store(StatusPedido statusPedido);
    StatusPedido update(StatusPedido statusPedido, Integer id);
    void mudarEstado(Integer id);
    void reordenarKanban(List<StatusPedidoReordenacaoDto> statusPedidos);
    StatusPedido findById(Integer id);
    StatusPedido findFirstOfKanbanOrder();
}
