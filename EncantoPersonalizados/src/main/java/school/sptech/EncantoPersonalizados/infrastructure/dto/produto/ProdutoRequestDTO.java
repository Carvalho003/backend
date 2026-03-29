package school.sptech.EncantoPersonalizados.infrastructure.dto.produto;

import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;

import java.util.List;

public record ProdutoRequestDTO(
         String descricao,
         String titulo,
         Integer temaId,
         Integer itemId
) {
}
