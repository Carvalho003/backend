package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.application.gateway.ItemProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.itemProduto.ItemProdutoUseCaseImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemProdutoUseCaseImplTest {

    @Mock
    private ItemProdutoGateway repository;

    @InjectMocks
    private ItemProdutoUseCaseImpl service;

    @Test
    @DisplayName("Deve cadastrar ItemProduto e retornar DTO corretamente")
    void deveCadastrarItemProduto() {
        ItemProdutoRequestDTO request = new ItemProdutoRequestDTO(
                "Caneca",
                29.90,
                10.00,
                5,
                10.0,
                12.0,
                300.0,
                8.0,
                "Cerâmica",
                25.90
        );

        ItemProduto entityMock = new ItemProduto();
        entityMock.setId(1);
        entityMock.setDescricao("Caneca");

        when(repository.save(any(ItemProduto.class))).thenReturn(entityMock);

        ItemProdutoResponseDTO response = service.cadastrar(request);

        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Caneca", response.descricao());
        verify(repository, times(1)).save(any(ItemProduto.class));
    }

    @Test
    @DisplayName("Deve retornar item ao buscar por ID existente")
    void deveBuscarPorId() {
        ItemProduto item = new ItemProduto();
        item.setId(10);

        when(repository.findById(10)).thenReturn(Optional.of(item));

        ItemProduto result = service.findByid(10);

        assertNotNull(result);
        assertEquals(10, result.getId());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar ID inexistente")
    void deveRetornarNullAoBuscarIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        ItemProduto result = service.findByid(99);

        assertNull(result);
    }

    @Test
    @DisplayName("Deve atualizar os dados de um item")
    void deveAtualizarItemProduto() {
        ItemProduto original = new ItemProduto();
        original.setId(5);
        original.setDescricao("Produto antigo");

        ItemProduto atualizado = new ItemProduto();
        atualizado.setDescricao("Produto novo");

        when(repository.findById(5)).thenReturn(Optional.of(original));
        when(repository.save(any(ItemProduto.class))).thenReturn(original);

        ItemProdutoResponseDTO result = service.update(5, atualizado);

        assertEquals("Produto novo", result.descricao());
        verify(repository, times(1)).save(original);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar item inexistente")
    void deveLancarErroNoUpdateDeItemInexistente() {
        when(repository.findById(55)).thenReturn(Optional.empty());

        ItemProduto atualizado = new ItemProduto();

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.update(55, atualizado));
    }

    @Test
    @DisplayName("Deve alternar o estado ativo/inativo do item")
    void deveMudarEstadoDoItem() {
        ItemProduto item = new ItemProduto();
        item.setId(3);
        item.setAtivo(true);

        when(repository.findById(3)).thenReturn(Optional.of(item));

        service.mudarEstado(3);

        assertFalse(item.getAtivo());
        verify(repository, times(1)).save(item);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar estado de item inexistente")
    void deveLancarErroAoMudarEstadoDeItemInexistente() {
        when(repository.findById(88)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.mudarEstado(88));
    }

    @Test
    @DisplayName("Deve retornar itens com preço menor que valor informado")
    void deveBuscarItensPorPreco() {
        ItemProduto item = new ItemProduto();
        item.setId(1);
        item.setPrecoVenda(20.0);

        when(repository.findByPrecoVendaLessThan(30.0))
                .thenReturn(List.of(item));

        List<ItemProdutoResponseDTO> result = service.buscarPorPrecoMenorQue(30.0);

        assertEquals(1, result.size());
        assertEquals(20.0, result.get(0).precoVenda());
        verify(repository, times(1)).findByPrecoVendaLessThan(30.0);
    }

    @Test
    @DisplayName("Deve listar os itens com paginação")
    void deveListarComPaginacao() {
        ItemProduto item = new ItemProduto();
        item.setId(1);

        Page<ItemProduto> page = new PageImpl<>(List.of(item));

        when(repository.filtrar("abc", true, PageRequest.of(0, 10)))
                .thenReturn(page);

        Page<ItemProduto> result = service.listar("abc", true, 0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
