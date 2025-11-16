package school.sptech.EncantoPersonalizados.dto.contraparte;


import jakarta.validation.constraints.NotBlank;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;

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
