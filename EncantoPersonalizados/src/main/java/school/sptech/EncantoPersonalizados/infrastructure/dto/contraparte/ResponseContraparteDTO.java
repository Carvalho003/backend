package school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte;

import jakarta.validation.constraints.NotBlank;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseContraparteDTO(
        @NotBlank
        String nome,
        String descricao,
        @NotBlank
        String segmento,
        @NotBlank
        String tipoContrato
) {}
