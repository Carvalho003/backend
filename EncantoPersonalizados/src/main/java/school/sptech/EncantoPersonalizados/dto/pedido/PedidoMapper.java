package school.sptech.EncantoPersonalizados.dto.pedido;

import school.sptech.EncantoPersonalizados.dto.cliente.ClienteMapper;
import school.sptech.EncantoPersonalizados.dto.cliente.ResponseClienteDTO;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoMapper;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoMapper;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;

import java.util.List;

public class PedidoMapper {


    public static Pedido toEntity(PedidoRequestDto dto) {
        Pedido pedido = new Pedido();
        pedido.setObservacoes(dto.observacoes());
        pedido.setOrigem(dto.origem());
        return pedido;
    }

    public static PedidoResponseDto toDto(Pedido pedido, PedidoStatusPedidoResponseDto statusAtual) {

        ResponseClienteDTO cliente = ClienteMapper.toDto(pedido.getCliente());
        List<ProdutosPedidoResponseDto> produtos = ProdutosPedidoMapper.toDtoList(pedido.getProdutoPedidos());
        List<PedidoStatusPedidoResponseDto> historicoPedidos = PedidoStatusPedidoMapper.toResponseDtoList(pedido.getPedidoStatusPedidos());

        return new PedidoResponseDto(
                pedido.getId(),
                pedido.getObservacoes(),
                pedido.getOrigem(),
                pedido.getDataLimite(),
                pedido.getPrecoTotal(),
                pedido.getPesoTotal(),
                cliente,
                produtos,
                pedido.isAtivo(),
                pedido.getCreatedAt(),
                pedido.getUpdatedAt(),
                statusAtual,
                historicoPedidos
        );

    }

    public static List<PedidoResponseDto> toDtoList(List<Pedido> pedidos, List<PedidoStatusPedidoResponseDto> statusAtuais) {
        return pedidos.stream()
                .map(pedido -> {
                    PedidoStatusPedidoResponseDto statusAtual = statusAtuais.stream()
                            .filter(status -> status.idPedido().equals(pedido.getId()))
                            .findFirst()
                            .orElse(null);
                    return toDto(pedido, statusAtual);
                })
                .toList();

    }
}
