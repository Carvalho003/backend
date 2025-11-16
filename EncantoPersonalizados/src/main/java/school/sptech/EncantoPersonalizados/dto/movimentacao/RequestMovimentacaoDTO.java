package school.sptech.EncantoPersonalizados.dto.movimentacao;

import jakarta.validation.constraints.NotBlank;

public record RequestMovimentacaoDTO(
        @NotBlank
        String tipo,
        String descricao,
        @NotBlank
        Double valor,
        @NotBlank
        Integer idContraparte,
        @NotBlank
        Integer idCategoriaMovimentacao
) {}