package school.sptech.EncantoPersonalizados.infrastructure.dto.produto;

import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ProdutoResponseDTO(
        Integer id,
        String titulo,
        String descricao,
        List<FotoProdutoResponseDTO> fotos,
        TemaProdutoResponseDTO tema,
        ItemProdutoResponseDTO item,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
