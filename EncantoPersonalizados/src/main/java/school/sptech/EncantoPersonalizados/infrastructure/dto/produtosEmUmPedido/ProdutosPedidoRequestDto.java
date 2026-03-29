package school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido;

public record   ProdutosPedidoRequestDto(
        Integer idProduto,
        Integer idPedido,
        Integer quantidade


) {
}
