package school.sptech.EncantoPersonalizados.core.application.usecase.produtoPedido;

import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProdutoPedidoUseCaseImpl implements ProdutoPedidoUseCase {

    private final ProdutoPedidoGateway produtoPedidoGateway;
    private final ProdutoGateway produtoGateway;
    private final PedidoGateway pedidoGateway;

    public ProdutoPedidoUseCaseImpl(
            ProdutoPedidoGateway produtoPedidoGateway,
            ProdutoGateway produtoGateway,
            PedidoGateway pedidoGateway
    ) {
        this.produtoPedidoGateway = produtoPedidoGateway;
        this.produtoGateway = produtoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public ProdutoPedido salvar(ProdutoPedido entity, Integer produtoId, Integer pedidoId) {
        Produto produto = produtoGateway.findById(produtoId).orElse(null);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        Pedido pedido = pedidoGateway.findById(pedidoId).orElse(null);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        entity.setPedido(pedido);
        entity.setProduto(produto);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setPesoUnitario(produto.getItemProduto().getPeso());
        Double precoUnitario;

        if (produto.getItemProduto().getPrecoPromocional() != null) {
            precoUnitario = produto.getItemProduto().getPrecoPromocional();
        } else {
            precoUnitario = produto.getItemProduto().getPrecoVenda();
        }
        entity.setPrecoUnitario(precoUnitario);

        entity.setPrecoTotal(entity.getQtdProduto() * entity.getPesoUnitario());
        entity.setPesoTotal(entity.getQtdProduto() * entity.getPesoUnitario());

        return produtoPedidoGateway.save(entity);
    }

    @Override
    public ProdutoPedido removerProduto(Integer id) {
        ProdutoPedido produtoPedido = this.findById(id);
        if (produtoPedido == null) throw new EntidadeNaoEncontradaException("Produto de pedido não encontrado");
        produtoPedidoGateway.deleteById(id);
        return produtoPedido;
    }

    @Override
    public List<?> atualizarProduto(Integer id, Integer quantidade) {
        ProdutoPedido produtoPedido = this.findById(id);
        if (produtoPedido == null) throw new EntidadeNaoEncontradaException("Produto de pedido não encontrado");
        Double precoTotalAntigo = produtoPedido.getPrecoTotal();
        Double pesoTotalAntigo = produtoPedido.getPesoTotal();

        produtoPedido.setQtdProduto(quantidade);
        produtoPedido.setPrecoTotal(produtoPedido.getPrecoUnitario() * quantidade);
        produtoPedido.setPesoTotal(produtoPedido.getPesoUnitario() * quantidade);

        Double diferencaPeso = produtoPedido.getPesoTotal() - pesoTotalAntigo;
        Double diferencaPreco = produtoPedido.getPrecoTotal() - precoTotalAntigo;

        List<?> novosTotaisMaisPedidoId = List.of(diferencaPeso, diferencaPreco, produtoPedido.getPedido().getId());

        produtoPedidoGateway.save(produtoPedido);
        return novosTotaisMaisPedidoId;
    }

    @Override
    public ProdutoPedido findById(Integer id) {
        return produtoPedidoGateway.findById(id).orElse(null);
    }
}
