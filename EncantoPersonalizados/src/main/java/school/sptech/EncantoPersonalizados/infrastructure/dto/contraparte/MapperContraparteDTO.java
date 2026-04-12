package school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte;

import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;

import java.util.List;

public class MapperContraparteDTO {
    public static Contraparte toEntity(RequestContraparteDTO dto) {
        var entity = new Contraparte();
        entity.setDescricao(dto.descricao());
        entity.setNome(dto.nome());
        entity.setSegmento(dto.segmento());
        entity.setTipoContrato(dto.tipoContrato());

        return entity;
    }

    public static ResponseContraparteDTO toDto(Contraparte contraparte) {
        var dto = new ResponseContraparteDTO(
                contraparte.getId(),
                contraparte.getNome(),
                contraparte.getDescricao(),
                contraparte.getSegmento(),
                contraparte.getTipoContrato()
        );

        return dto;
    }
}
