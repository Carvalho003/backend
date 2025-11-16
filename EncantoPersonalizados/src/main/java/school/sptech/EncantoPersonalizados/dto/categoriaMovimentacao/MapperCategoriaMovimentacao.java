package school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao;

import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;

public class MapperCategoriaMovimentacao {
    public static CategoriaMovimentacao toEntity(RequestCategoriaMovimentacaoDTO dto) {
        var entity = new CategoriaMovimentacao();
        entity.setDescricao(dto.descricao());
        return entity;
    }

    public static ResponseCategoriaMovimentacaoDTO toDTO(CategoriaMovimentacao entity) {
        return new ResponseCategoriaMovimentacaoDTO(
                entity.getId(),
                entity.getDescricao()
        );
    }
}
