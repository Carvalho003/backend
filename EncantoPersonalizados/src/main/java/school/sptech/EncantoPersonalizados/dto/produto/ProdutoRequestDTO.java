package school.sptech.EncantoPersonalizados.dto.produto;

import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.entities.FotoProduto;

import java.util.List;

public record ProdutoRequestDTO(
         String descricao,
         String titulo,
         Integer temaId,
         Integer itemId
) {
}
