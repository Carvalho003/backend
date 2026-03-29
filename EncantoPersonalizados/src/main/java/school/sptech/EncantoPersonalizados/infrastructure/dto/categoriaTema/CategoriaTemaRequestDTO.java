package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaTemaRequestDTO(
        @Schema(description = "Titulo da categorias do tema", example = "Infantil")
        @NotNull
        @NotBlank
        String titulo
) {
}
