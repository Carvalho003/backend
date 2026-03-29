package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.core.application.usecase.produtoPedido.ProdutoPedidoUseCaseImpl;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProdutoPedidoUseCaseImplTest {
    @Mock
    ProdutoPedidoGateway repository;

    @Mock
    ProdutoGateway produtoRepository;

    @Mock
    PedidoGateway pedidoGateway;

    @InjectMocks
    ProdutoPedidoUseCaseImpl service;

    //function salvar
    @Test
    @DisplayName("Quando for salvar ProdutoPedido e o produto não existir deve lançar exceção")
    void QuandoSalvarProdutoPedidoMasProdutoNaoExisteLancarExcecao(){
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.salvar(produtoPedido, 1, 1)
        );

        assertEquals("Produto não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Quando for salvar ProdutoPedido e o pedido não existir deve lançar exceção")
    void QuandoSalvarProdutoPedidoMasPedidoNaoExisteLancarExcecao(){
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Produto produto = new Produto();
        Pedido pedido = new Pedido();

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        when(pedidoGateway.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.salvar(produtoPedido, 1, 1)
        );

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve salvar ProdutoPedido quando produto e pedido existirem")
    void salvarProdutoPedidoComSucesso() {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setQtdProduto(1);

        ItemProduto item = new ItemProduto();
        item.setPeso(1.0);
        item.setPrecoVenda(1.0);
        item.setPrecoPromocional(null);

        Produto produto = new Produto();
        produto.setItemProduto(item);
        produto.setId(1);

        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(pedidoGateway.findById(1)).thenReturn(Optional.of(pedido));
        when(repository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);

        ProdutoPedido resultado = service.salvar(produtoPedido, 1, 1);

        assertEquals(pedido, resultado.getPedido());
        assertEquals(produto, resultado.getProduto());

        assertEquals(1.0, resultado.getPrecoUnitario());
        assertEquals(1.0, resultado.getPrecoTotal());
        assertEquals(1.0, resultado.getPesoTotal());

        assertNotNull(resultado.getCreatedAt());

        verify(repository, times(1)).save(produtoPedido);
    }

    @Test
    @DisplayName("Deve salvar ProdutoPedido usando preco promocional quando produto e pedido existirem")
    void salvarProdutoPedidoComSucessoComPrecoPromocional() {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setQtdProduto(1);

        ItemProduto item = new ItemProduto();
        item.setPeso(1.0);
        item.setPrecoVenda(1.0);
        item.setPrecoPromocional(0.5);

        Produto produto = new Produto();
        produto.setItemProduto(item);
        produto.setId(1);

        Pedido pedido = new Pedido();
        pedido.setId(2);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(pedidoGateway.findById(2)).thenReturn(Optional.of(pedido));
        when(repository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);

        ProdutoPedido resultado = service.salvar(produtoPedido, 1, 2);

        assertEquals(pedido, resultado.getPedido());
        assertEquals(produto, resultado.getProduto());

        assertEquals(0.5, resultado.getPrecoUnitario());
        assertEquals(1.0, resultado.getPrecoTotal());
        assertEquals(1.0, resultado.getPesoTotal());

        assertNotNull(resultado.getCreatedAt());

        verify(repository, times(1)).save(produtoPedido);
    }


    //function atualizarProduto
    @Test
    @DisplayName("Se for atualizar quantidade de produto em ProdutoPedido mas ProdutoPedido não existir lançar exceção")
    void QuandoAtualizarQuantidadeDeProdutoDeProdutoPedidoInexistenteLancarExcecao(){
        when(repository.findById(1)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.atualizarProduto(1, 1)
        );

        assertEquals("Produto de pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar ProdutoPedido quando ele existir")
    void AtualizarProdutoPedidoQuandoEleExistir() {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setId(1);
        produtoPedido.setQtdProduto(1);
        produtoPedido.setPrecoUnitario(1.0);
        produtoPedido.setPesoUnitario(1.0);
        produtoPedido.setPrecoTotal(1.0);
        produtoPedido.setPesoTotal(1.0);

        Pedido pedido = new Pedido();
        pedido.setId(2);
        produtoPedido.setPedido(pedido);

        when(repository.findById(1)).thenReturn(Optional.of(produtoPedido));

        List<?> resultado = service.atualizarProduto(1, 2);

        assertEquals(3, resultado.size());
        assertEquals(1.0, resultado.get(0));
        assertEquals(1.0, resultado.get(1));
        assertEquals(2, resultado.get(2));

        verify(repository, times(1)).save(produtoPedido);

        assertEquals(2, produtoPedido.getQtdProduto());
        assertEquals(2.0, produtoPedido.getPrecoTotal());
        assertEquals(2.0, produtoPedido.getPesoTotal());
    }


    //function removerProduto
    @Test
    @DisplayName("Se for deletar ProdutoPedido mas ProdutoPedido não existir lançar exceção")
    void QuandoDeletarProdutoPedidoInexistenteLancarExcecao(){
        when(repository.findById(1)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.removerProduto(1)
        );

        assertEquals("Produto de pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Remover ProdutoPedido quando ele existir")
    void RemoverProdutoPedido(){
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(produtoPedido));

        doNothing().when(repository).deleteById(produtoPedido.getId());

        service.removerProduto(1);

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).deleteById(produtoPedido.getId());


    }


    // function findById
    @Test
    @DisplayName("Quando buscar por ID que não existe retornar NULL")
    void RetornarNullSeIdNaoExistirTest() {
        ProdutoPedido pedido = new ProdutoPedido();
        pedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.empty());

        ProdutoPedido resultado = service.findById(1);

        assertNull(resultado);

    }

    @Test
    @DisplayName("Quando buscar por ID existente deve retornar ProdutoPedido com esse ID")
    void quandoBuscarPorIdQueExisteRetornarProdutoPedidoCorrespondenteTest() {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(produtoPedido));

        ProdutoPedido resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals(1, produtoPedido.getId());

    }


}