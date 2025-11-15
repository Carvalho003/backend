package school.sptech.EncantoPersonalizados.dto.fotoProduto;

import java.time.LocalDateTime;

public record FotoProdutoResponseDTO(
        Integer id,
        String foto,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {

}
