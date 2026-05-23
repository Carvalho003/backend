package school.sptech.EncantoPersonalizados.core.application.usecase.pedido;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PedidoUseCase {
    Pedido findById(Integer id);
    PedidoCreatedResponseDto store(PedidoRequestDto pedidoDto);
    Page<PedidoResponseDto> listar(String search, Integer page, Boolean ativo, LocalDate inicio, LocalDate fim, Integer size);
    Page<PedidoResponseDto> listarAvancado(
            String search,
            Integer page,
            Boolean ativo,
            LocalDate inicio,
            LocalDate fim,
            Integer size,
            String origem,
            Integer statusId,
            LocalDate createdAtInicio,
            LocalDate createdAtFim,
            LocalDate dataLimiteInicio,
            LocalDate dataLimiteFim,
            Double valorMin,
            Double valorMax,
            String sortBy,
            String sortDirection
    );
    PedidoResponseDto getById(Integer id);
    PedidoCreatedResponseDto update(Integer id, PedidoRequestDto pedidoDto);
    void mudarEstado(Integer id);
    void mudarStatus(PedidoStatusPedidoRequestDto dto);
    void atualizarPrecoPesoDoPedido(boolean aumentar, Double preco, Double peso, Integer pedidoId);
}
