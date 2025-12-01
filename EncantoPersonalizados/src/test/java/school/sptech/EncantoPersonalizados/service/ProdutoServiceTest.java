package school.sptech.EncantoPersonalizados.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @Test
    @DisplayName("Deve listar produtos corretamente com filtros preenchidos")
    void deveListarProdutosComFiltrosPreenchidos() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");

        Page<Produto> paginaSimulada = new PageImpl<>(List.of(produto));

        when(repository.filtrar(eq("busca"), eq("categoria"), eq("tema"), eq("item"), eq(true), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<Produto> resultado = service.get("busca", "categoria", "tema", "item", true, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Produto Teste", resultado.getContent().get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve listar produtos corretamente com filtros vazios convertidos para null")
    void deveListarProdutosComFiltrosVaziosConvertidosParaNull() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");

        Page<Produto> paginaSimulada = new PageImpl<>(List.of(produto));

        when(repository.filtrar(eq(null), eq(null), eq(null), eq(null), eq(true), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<Produto> resultado = service.get("", "   ", null, "", true, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deve retornar produto existente por ID")
    void deveRetornarProdutoExistentePorId() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");

        when(repository.findById(1)).thenReturn(Optional.of(produto));

        Produto resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar produto inexistente por ID")
    void deveRetornarNullAoBuscarProdutoInexistentePorId() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        Produto resultado = service.findById(999);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve mudar estado de ativo para inativo")
    void deveMudarEstadoDeAtivoParaInativo() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");
        produto.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        service.mudarEstado(1);

        assertFalse(produto.getAtivo());
    }

    @Test
    @DisplayName("Deve mudar estado de inativo para ativo")
    void deveMudarEstadoDeInativoParaAtivo() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");
        produto.setAtivo(false);

        when(repository.findById(1)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        service.mudarEstado(1);

        assertTrue(produto.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar estado de produto inexistente")
    void deveLancarExcecaoAoMudarEstadoDeProdutoInexistente() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> service.mudarEstado(999)
        );
    }

    @Test
    @DisplayName("Deve salvar produto corretamente")
    void deveSalvarProdutoCorretamente() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setTitulo("Produto Teste");

        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = service.store(produto);

        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.getTitulo());
    }
}