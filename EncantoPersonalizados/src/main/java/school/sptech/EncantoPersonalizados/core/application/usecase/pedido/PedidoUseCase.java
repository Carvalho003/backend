package school.sptech.EncantoPersonalizados.core.application.usecase.pedido;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;

public interface PedidoUseCase {
    Pedido findById(Integer id);
    PedidoCreatedResponseDto store(PedidoRequestDto pedidoDto);
    Page<PedidoResponseDto> listar(String search, Integer page, Boolean ativo);
    PedidoResponseDto getById(Integer id);
    PedidoCreatedResponseDto update(Integer id, PedidoRequestDto pedidoDto);
    void mudarEstado(Integer id);
    void mudarStatus(PedidoStatusPedidoRequestDto dto);
    void atualizarPrecoPesoDoPedido(boolean aumentar, Double preco, Double peso, Integer pedidoId);
}
