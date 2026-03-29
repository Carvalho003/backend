package school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido;

import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

import java.util.List;

public class ProdutosPedidoMapper {

    public static ProdutoPedido toEntity(ProdutosPedidoRequestDto dto) {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setQtdProduto(dto.quantidade());
        return produtoPedido;
    }

    public static ProdutosPedidoResponseDto toDto(ProdutoPedido entity) {
        return new ProdutosPedidoResponseDto(
                entity.getId(),
                entity.getProduto().getId(),
                entity.getPedido().getId(),
                entity.getQtdProduto(),
                entity.getPesoTotal(),
                entity.getPesoUnitario(),
                entity.getPrecoUnitario(),
                entity.getPrecoTotal(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static List<ProdutosPedidoResponseDto> toDtoList(List<ProdutoPedido> entities) {
        return entities.stream()
                .map(ProdutosPedidoMapper::toDto)
                .toList();
    }
}
