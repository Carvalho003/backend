package school.sptech.EncantoPersonalizados.facade;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.core.domain.exception.ItemProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.domain.exception.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.application.gateway.ItemProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.TemaProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.produto.ProdutoUseCaseImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoUseCaseImplTest {

    @InjectMocks
    private ProdutoUseCaseImpl facade;

    @Mock
    private ProdutoGateway produtoGateway;

    @Mock
    private TemaProdutoGateway temaProdutoGateway;

    @Mock
    private ItemProdutoGateway itemProdutoGateway;

    @Test
    @DisplayName("Deve salvar produto corretamente quando item e tema existem")
    void deveSalvarProdutoCorretamente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao", 1, 1);

        ItemProduto item = new ItemProduto();
        item.setId(1);
        item.setDescricao("Item Teste");

        TemaProduto tema = new TemaProduto();
        tema.setId(1);
        tema.setDescricao("Tema Teste");

        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Titulo");
        produto.setDescricao("Descricao");
        produto.setItemProduto(item);
        produto.setTemaProduto(tema);

        when(itemProdutoGateway.findById(1)).thenReturn(Optional.of(item));
        when(temaProdutoGateway.findById(1)).thenReturn(Optional.of(tema));
        when(produtoGateway.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO resultado = facade.storeFull(dto);

        assertNotNull(resultado);
        assertEquals("Titulo", resultado.titulo());
        assertEquals("Descricao", resultado.descricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com item inexistente")
    void deveLancarExcecaoAoSalvarProdutoComItemInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao", 999, 1);

        when(itemProdutoGateway.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemProdutoNaoEncontradoException.class, () -> facade.storeFull(dto));
    }


    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com tema inexistente")
    void deveLancarExcecaoAoSalvarProdutoComTemaInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao", 1, 999);

        ItemProduto item = new ItemProduto();
        item.setId(1);

        when(itemProdutoGateway.findById(anyInt())).thenReturn(Optional.of(item));
        when(temaProdutoGateway.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(TemaProdutoNaoEncontradoException.class, () -> facade.storeFull(dto));
    }

    @Test
    @DisplayName("Deve atualizar produto corretamente quando item e tema existem")
    void deveAtualizarProdutoCorretamente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Novo Titulo", "Novo Titulo",  1, 1);

        ItemProduto item = new ItemProduto();
        item.setId(1);

        TemaProduto tema = new TemaProduto();
        tema.setId(1);

        Produto antigo = new Produto();
        antigo.setId(1);
        antigo.setTitulo("Antigo");
        antigo.setDescricao("Antiga");

        when(produtoGateway.findById(1)).thenReturn(Optional.of(antigo));
        when(itemProdutoGateway.findById(1)).thenReturn(Optional.of(item));
        when(temaProdutoGateway.findById(1)).thenReturn(Optional.of(tema));
        when(produtoGateway.save(any(Produto.class))).thenReturn(antigo);

        ProdutoResponseDTO resultado = facade.update(dto, 1);

        assertNotNull(resultado);
        assertEquals("Novo Titulo", antigo.getTitulo());
        assertEquals("Novo Titulo", antigo.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  1, 1);

        when(produtoGateway.findById(999)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> facade.update(dto, 999));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto com item inexistente")
    void deveLancarExcecaoAoAtualizarProdutoComItemInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao", 999, 1);

        Produto antigo = new Produto();
        antigo.setId(1);

        when(produtoGateway.findById(1)).thenReturn(Optional.of(antigo));
        when(itemProdutoGateway.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemProdutoNaoEncontradoException.class, () -> facade.update(dto, 1));
    }

}
