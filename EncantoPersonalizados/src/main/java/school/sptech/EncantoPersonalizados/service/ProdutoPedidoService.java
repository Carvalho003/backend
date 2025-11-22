package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.ProdutoPedido;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ProdutoPedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoPedidoService {

    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    public ProdutoPedidoService(ProdutoPedidoRepository produtoPedidoRepository, ProdutoService produtoService, PedidoService pedidoService) {
        this.produtoPedidoRepository = produtoPedidoRepository;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
    }

    public ProdutoPedido salvar(ProdutoPedido entity, Integer produtoId, Integer pedidoId){
        // verificar se existe o produto
        Produto produto = produtoService.findById(produtoId);
        if(produto == null){
            throw new RuntimeException("Produto não encontrado");
        }

        Pedido pedido = pedidoService.findById(pedidoId);
        // verificar se existe o pedido
        if(pedido == null){
            throw new RuntimeException("Pedido não encontrado");
        }


        entity.setPedido(pedido);
        entity.setProduto(produto);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setPesoUnitario(produto.getItemProduto().getPeso());
        Double precoUnitario = 0.0;

        if(produto.getItemProduto().getPrecoPromocional() != null){
            precoUnitario = produto.getItemProduto().getPrecoPromocional();
        }else{
            precoUnitario = produto.getItemProduto().getPrecoVenda();
        }
        entity.setPrecoUnitario(precoUnitario);

        entity.setPrecoTotal(entity.getQtdProduto() * entity.getPesoUnitario());;
        entity.setPesoTotal(entity.getQtdProduto() * entity.getPesoUnitario());





        return produtoPedidoRepository.save(entity);
    }

    public ProdutoPedido removerProduto(Integer id )
    {
        ProdutoPedido produtoPedido = this.findById(id);
        if(produtoPedido == null) throw new EntidadeNaoEncontradaException("Produto de pedido não encontrado");
        produtoPedidoRepository.delete(produtoPedido);
        return produtoPedido;

    }

    public List<?> atualizarProduto(Integer id , Integer quantidade)
    {
        ProdutoPedido produtoPedido = this.findById(id);
        if(produtoPedido == null) throw new EntidadeNaoEncontradaException("Produto de pedido não encontrado");
        Double precoTotalAntigo = produtoPedido.getPrecoTotal();
        Double pesoTotalAntigo = produtoPedido.getPesoTotal();

        produtoPedido.setQtdProduto(quantidade);
        produtoPedido.setPrecoTotal( produtoPedido.getPrecoUnitario() * quantidade );
        produtoPedido.setPesoTotal( produtoPedido.getPesoUnitario() * quantidade );

        Double diferencaPeso = produtoPedido.getPesoTotal() - pesoTotalAntigo;
        Double diferencaPreco = produtoPedido.getPrecoTotal() - precoTotalAntigo;


        List<?> novosTotaisMaisPedidoId = List.of(diferencaPeso, diferencaPreco, produtoPedido.getPedido().getId());

        produtoPedidoRepository.save(produtoPedido);
        return novosTotaisMaisPedidoId;

    }

    public ProdutoPedido findById(Integer id){
        return produtoPedidoRepository.findById(id).orElse(null);
    }

}
