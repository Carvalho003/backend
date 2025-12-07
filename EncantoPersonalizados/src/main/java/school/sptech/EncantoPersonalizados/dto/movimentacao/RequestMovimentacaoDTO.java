package school.sptech.EncantoPersonalizados.dto.movimentacao;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RequestMovimentacaoDTO(
        @NotBlank
        String tipo,
        String descricao,
        @NotBlank
        Double valor,
        @NotBlank
        String statusPagamento,
        @NotBlank
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        @NotBlank
        Integer idContraparte,
        @NotBlank
        Integer idCategoriaMovimentacao
) {}