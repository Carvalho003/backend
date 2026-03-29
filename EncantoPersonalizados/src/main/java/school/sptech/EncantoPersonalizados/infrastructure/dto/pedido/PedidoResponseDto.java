package school.sptech.EncantoPersonalizados.infrastructure.dto.pedido;

import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.ResponseClienteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioResponseDTO;

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
