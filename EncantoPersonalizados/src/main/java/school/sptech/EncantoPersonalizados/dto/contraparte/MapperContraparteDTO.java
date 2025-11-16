package school.sptech.EncantoPersonalizados.dto.contraparte;

import school.sptech.EncantoPersonalizados.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.entities.Contraparte;

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
                contraparte.getNome(),
                contraparte.getDescricao(),
                contraparte.getSegmento(),
                contraparte.getTipoContrato()
        );

        return dto;
    }
}
