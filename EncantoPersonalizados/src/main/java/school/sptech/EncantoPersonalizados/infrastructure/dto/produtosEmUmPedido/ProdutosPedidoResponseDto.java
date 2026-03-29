package school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido;

import java.time.LocalDateTime;

public record ProdutosPedidoResponseDto(
        Integer id,
        Integer idProduto,
        Integer idPedido,
        Integer quantidade,
        Double pesoTotal,
        Double pesoUnitario,
        Double precoUnitario,
        Double precoTotal,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
