package school.sptech.EncantoPersonalizados.dto.pedido;

import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;

import java.util.List;

public record PedidoRequestDto(
        String observacoes,
        String origem,
        Integer clienteId,
        List<ProdutosPedidoRequestDto> produtos
) {
}
