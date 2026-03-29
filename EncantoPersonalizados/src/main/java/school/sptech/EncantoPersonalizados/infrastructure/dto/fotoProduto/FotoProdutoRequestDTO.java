package school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto;

import io.swagger.v3.oas.annotations.media.Schema;

public record FotoProdutoRequestDTO(
        @Schema(description = "Base 64 da foto do produto")
        String foto
) {
}
