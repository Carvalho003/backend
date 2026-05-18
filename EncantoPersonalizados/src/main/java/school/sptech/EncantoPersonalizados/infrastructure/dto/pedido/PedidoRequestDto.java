package school.sptech.EncantoPersonalizados.infrastructure.dto.pedido;

import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;

import java.time.LocalDate;
import java.util.List;

public record PedidoRequestDto(
        String observacoes,
        String origem,
        Integer clienteId,
        Integer usuarioId,
        LocalDate dataLimite,
        List<ProdutosPedidoRequestDto> produtos
) {
}
