package school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestMovimentacaoDTO(
        @NotBlank
        String tipo,
        String descricao,
        @NotNull
        Double valor,
        @NotBlank
        String statusPagamento,

        LocalDate dataVencimento,
        @NotNull
        LocalDate dataPagamento,
        @NotNull
        Integer idContraparte,
        @NotNull
        Integer idCategoriaMovimentacao
) {}
