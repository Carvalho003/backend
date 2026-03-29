package school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte;


import jakarta.validation.constraints.NotBlank;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;

import java.time.LocalDateTime;
import java.util.List;

public record RequestContraparteDTO(
        @NotBlank
        String nome,
        String descricao,
        @NotBlank
        String segmento,
        @NotBlank
        String tipoContrato
) {}
