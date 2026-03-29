package school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao;

import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.ResponseContraparteDTO;

import java.time.LocalDate;

public record ResponseMovimentacaoDTO (
    String tipo,
    String descricao,
    Double valor,
    String statusPagamento,
    LocalDate dataVencimento,
    LocalDate dataPagamento,
    ResponseCategoriaMovimentacaoDTO categoria,
    ResponseContraparteDTO contraparte
) {}
