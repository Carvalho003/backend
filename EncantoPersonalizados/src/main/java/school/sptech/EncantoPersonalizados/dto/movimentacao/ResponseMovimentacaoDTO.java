package school.sptech.EncantoPersonalizados.dto.movimentacao;

import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;

public record ResponseMovimentacaoDTO (
    String tipo,
    String descricao,
    Double valor,
    ResponseCategoriaMovimentacaoDTO categoria,
    ResponseContraparteDTO contraparte
) {}
