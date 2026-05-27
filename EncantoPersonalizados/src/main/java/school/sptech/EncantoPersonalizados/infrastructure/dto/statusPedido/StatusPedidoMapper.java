package school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido;

import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;

import java.util.List;

public record StatusPedidoMapper() {

    public static StatusPedidoResponseDto toResponseDto(StatusPedido statusPedido) {
        return new StatusPedidoResponseDto(
                statusPedido.getId(),
                statusPedido.getStatus(),
                statusPedido.getCor(),
                statusPedido.getOrdemKanban(),
                statusPedido.getRole(),
                statusPedido.getCreatedAt(),
                statusPedido.getUpdatedAt()
        );
    }

    public static List<StatusPedidoResponseDto> toResponseDtoList(List<StatusPedido> statusPedidos) {
        return statusPedidos.stream()
                .map(StatusPedidoMapper::toResponseDto)
                .toList();
    }

    public static StatusPedido toEntity(StatusPedidoRequestDto dto) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setStatus(dto.status());
        statusPedido.setCor(dto.cor());
        statusPedido.setOrdemKanban(dto.ordemKanban());
        statusPedido.setRole(dto.role());
        return statusPedido;
    }
}
