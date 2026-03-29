package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoResponseDTO;

import java.util.List;

public record CategoriaTemaResponseDTO(
        Integer id,
        @Schema(description = "Titulo da categorias do tema", example = "Infantil")
        String titulo,
        List<TemaProdutoResponseDTO> temas

) {
}
