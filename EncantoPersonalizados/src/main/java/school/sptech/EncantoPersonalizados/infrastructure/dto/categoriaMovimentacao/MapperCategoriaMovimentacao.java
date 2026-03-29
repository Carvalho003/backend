package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao;

import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;

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
