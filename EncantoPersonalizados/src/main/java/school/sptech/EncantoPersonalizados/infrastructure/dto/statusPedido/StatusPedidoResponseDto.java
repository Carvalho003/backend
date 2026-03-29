package school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StatusPedidoResponseDto(
        Integer id,
        String status,
        String cor,
        Integer ordemKanban,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
