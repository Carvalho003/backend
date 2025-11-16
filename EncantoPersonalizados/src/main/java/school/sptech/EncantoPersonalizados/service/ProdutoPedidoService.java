package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.ProdutoPedido;
import school.sptech.EncantoPersonalizados.repository.ProdutoPedidoRepository;

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

    public ProdutoPedido salvar(ProdutoPedido entity){
        // verificar se existe o produto
        Produto produto = produtoService.findById(entity.getProduto().getId());
        if(produto == null){
            throw new RuntimeException("Produto não encontrado");
        }
        // verificar se existe o pedido
        if(pedidoService.findById(entity.getPedido().getId()) == null){
            throw new RuntimeException("Pedido não encontrado");
        }



        return produtoPedidoRepository.save(entity);
    }

    public ProdutoPedido findById(Integer id){
        return produtoPedidoRepository.findById(id).orElse(null);
    }

}
