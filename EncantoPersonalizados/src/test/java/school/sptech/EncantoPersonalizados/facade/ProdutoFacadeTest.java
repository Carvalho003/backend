package school.sptech.EncantoPersonalizados.facade;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.ItemProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.exceptions.ItemProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.exceptions.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.service.FotoProdutoService;
import school.sptech.EncantoPersonalizados.service.ItemProdutoService;
import school.sptech.EncantoPersonalizados.service.ProdutoService;
import school.sptech.EncantoPersonalizados.service.TemaProdutoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoFacadeTest {

    @InjectMocks
    private ProdutoFacade facade;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private TemaProdutoService temaProdutoService;

    @Mock
    private ItemProdutoService itemProdutoService;

    @Mock
    private FotoProdutoService fotoProdutoService;

    @Test
    @DisplayName("Deve salvar produto corretamente quando item e tema existem")
    void deveSalvarProdutoCorretamente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao", 1, 1);

        ItemProduto item = new ItemProduto();
        item.setId(1);
        item.setDescricao("Item Teste"); // <-- usar descricao

        TemaProduto tema = new TemaProduto();
        tema.setId(1);
        tema.setDescricao("Tema Teste");

        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Titulo");
        produto.setDescricao("Descricao");
        produto.setItemProduto(item);
        produto.setTemaProduto(tema);

        when(itemProdutoService.findByid(1)).thenReturn(item);
        when(temaProdutoService.findById(1)).thenReturn(tema);
        when(produtoService.store(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO resultado = facade.store(dto);

        assertNotNull(resultado);
        assertEquals("Titulo", resultado.titulo());
        assertEquals("Descricao", resultado.descricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com item inexistente")
    void deveLancarExcecaoAoSalvarProdutoComItemInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  999, 1);

        when(itemProdutoService.findByid(999)).thenReturn(null);

        assertThrows(ItemProdutoNaoEncontradoException.class, () -> facade.store(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com tema inexistente")
    void deveLancarExcecaoAoSalvarProdutoComTemaInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  1, 999);

        ItemProduto item = new ItemProduto();
        item.setId(1);

        when(itemProdutoService.findByid(1)).thenReturn(item);
        when(temaProdutoService.findById(999)).thenReturn(null);

        assertThrows(TemaProdutoNaoEncontradoException.class, () -> facade.store(dto));
    }

    @Test
    @DisplayName("Deve atualizar produto corretamente quando item e tema existem")
    void deveAtualizarProdutoCorretamente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Novo Titulo", "Nova Descricao",  1, 1);

        ItemProduto item = new ItemProduto();
        item.setId(1);

        TemaProduto tema = new TemaProduto();
        tema.setId(1);

        Produto antigo = new Produto();
        antigo.setId(1);
        antigo.setTitulo("Antigo");
        antigo.setDescricao("Antiga");

        when(produtoService.findById(1)).thenReturn(antigo);
        when(itemProdutoService.findByid(1)).thenReturn(item);
        when(temaProdutoService.findById(1)).thenReturn(tema);
        when(produtoService.store(any(Produto.class))).thenReturn(antigo);

        ProdutoResponseDTO resultado = facade.update(dto, 1);

        assertNotNull(resultado);
        assertEquals("Novo Titulo", antigo.getTitulo());
        assertEquals("Nova Descricao", antigo.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  1, 1);

        when(produtoService.findById(999)).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () -> facade.update(dto, 999));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto com item inexistente")
    void deveLancarExcecaoAoAtualizarProdutoComItemInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  999, 1);

        Produto antigo = new Produto();
        antigo.setId(1);

        when(produtoService.findById(1)).thenReturn(antigo);
        when(itemProdutoService.findByid(999)).thenReturn(null);

        assertThrows(ItemProdutoNaoEncontradoException.class, () -> facade.update(dto, 1));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto com tema inexistente")
    void deveLancarExcecaoAoAtualizarProdutoComTemaInexistente() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Titulo", "Descricao",  1, 999);

        Produto antigo = new Produto();
        antigo.setId(1);

        ItemProduto item = new ItemProduto();
        item.setId(1);

        when(produtoService.findById(1)).thenReturn(antigo);
        when(itemProdutoService.findByid(1)).thenReturn(item);
        when(temaProdutoService.findById(999)).thenReturn(null);

        assertThrows(TemaProdutoNaoEncontradoException.class, () -> facade.update(dto, 1));
    }
}