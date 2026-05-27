package school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido;

import java.time.LocalDateTime;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedidoRole;

public record StatusPedidoResponseDto(
        Integer id,
        String status,
        String cor,
        Integer ordemKanban,
        StatusPedidoRole role,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
