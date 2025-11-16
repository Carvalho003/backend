package school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido;

import java.time.LocalDateTime;

public record PedidoStatusPedidoResponseDto(
    Integer id,
    Integer idPedido,
    Integer idStatusPedido,
    Boolean statusAtual,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
