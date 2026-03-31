package school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto;

import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaResponseDTO;
import java.time.LocalDateTime;

public record TemaProdutoResponseDTO(
        Integer id,
        String descricao,
        CategoriaTemaResponseDTO categoriaTema,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {}