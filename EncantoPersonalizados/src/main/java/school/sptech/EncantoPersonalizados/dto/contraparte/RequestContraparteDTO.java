package school.sptech.EncantoPersonalizados.dto.contraparte;


import school.sptech.EncantoPersonalizados.entities.Movimentacao;

import java.time.LocalDateTime;
import java.util.List;

public record RequestContraparteDTO(
        Integer id,
        String nome,
        String descricao,
        String segmento,
        String tipoContrato
) {}
