package school.sptech.EncantoPersonalizados.dto.pedido;

import java.time.LocalDateTime;

public record PedidoCreatedResponseDto(
        Integer id,
        String observacoes,
        String origem,
        LocalDateTime dataLimite
) {
}
