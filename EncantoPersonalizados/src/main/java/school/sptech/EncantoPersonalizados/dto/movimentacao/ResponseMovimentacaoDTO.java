package school.sptech.EncantoPersonalizados.dto.movimentacao;

import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;

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
