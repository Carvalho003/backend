package school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto;

import java.time.LocalDateTime;

public record TemaProdutoResponseDTO(
        Integer id,
        String descricao,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
