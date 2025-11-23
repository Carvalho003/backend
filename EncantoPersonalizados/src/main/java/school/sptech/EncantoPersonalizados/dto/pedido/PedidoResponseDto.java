package school.sptech.EncantoPersonalizados.dto.pedido;

import school.sptech.EncantoPersonalizados.dto.cliente.ResponseClienteDTO;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDto(
    Integer id,
    String observacoes,
    String origem,
    LocalDateTime dataLimite,
    Double precoTotal,
    Double pesoTotal,
    ResponseClienteDTO cliente,
    UsuarioResponseDTO usuario,
    List<ProdutosPedidoResponseDto> produtos,
    Boolean ativo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    PedidoStatusPedidoResponseDto statusAtual,
    List<PedidoStatusPedidoResponseDto> historicoStatus
) {
}
