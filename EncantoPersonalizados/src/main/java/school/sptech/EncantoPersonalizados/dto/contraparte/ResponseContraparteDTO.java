package school.sptech.EncantoPersonalizados.dto.contraparte;

import school.sptech.EncantoPersonalizados.dto.movimentacao.ResponseMovimentacaoDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseContraparteDTO(
        Integer id,
        String nome,
        String descricao,
        String segmento,
        String tipoContrato,
        List<ResponseMovimentacaoDTO> movimentacoes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
