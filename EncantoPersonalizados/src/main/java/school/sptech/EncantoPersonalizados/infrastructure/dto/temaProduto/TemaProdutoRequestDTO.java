package school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TemaProdutoRequestDTO(
        @Schema(description = "Descrição do que é o tema", example = "Barbie")
        @NotBlank
        @NotNull
        String descricao,
        @NotNull
        @Schema(description = "Id da categoria de tema", example = "1")
        Integer categoriaTemaId
) {
}
