package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao;

import jakarta.validation.constraints.NotBlank;

public record RequestCategoriaMovimentacaoDTO (
        @NotBlank(message = "Descrição da categoria é obrigatório")
        String descricao
) {}
