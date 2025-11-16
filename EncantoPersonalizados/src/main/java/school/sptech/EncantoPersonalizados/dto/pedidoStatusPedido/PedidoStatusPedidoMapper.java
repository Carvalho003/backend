package school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido;

import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.entities.ProdutoPedido;

import java.util.List;

public class PedidoStatusPedidoMapper {

    public static PedidoStatusPedidoResponseDto toResponseDto(PedidoStatusPedido entity) {
        return new PedidoStatusPedidoResponseDto(
            entity.getId(),
            entity.getPedido().getId(),
            entity.getStatus().getId(),
            entity.isStatusAtual(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );

    }

    public static List<PedidoStatusPedidoResponseDto> toResponseDtoList(List<PedidoStatusPedido> entities) {
        return entities.stream()
            .map(PedidoStatusPedidoMapper::toResponseDto)
            .toList();
    }
}
