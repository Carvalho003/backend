package school.sptech.EncantoPersonalizados.dto.movimentacao;

public record RequestMovimentacaoDTO(
        String tipo,
        String descricao,
        Double valor,
        Integer idContraparte,
        Integer idCategoriaMovimentacao
) {}