package school.sptech.EncantoPersonalizados.core.application.usecase.produtoPedido;

import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

import java.util.List;

public interface ProdutoPedidoUseCase {
    ProdutoPedido salvar(ProdutoPedido entity, Integer produtoId, Integer pedidoId);
    ProdutoPedido removerProduto(Integer id);
    List<?> atualizarProduto(Integer id, Integer quantidade);
    ProdutoPedido findById(Integer id);
}
