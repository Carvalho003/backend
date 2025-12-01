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
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.TemaProdutoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemaProdutoServiceTest {

    @InjectMocks
    private TemaProdutoService service;

    @Mock
    private TemaProdutoRepository repository;

    @Mock
    private CategoriaTemaService categoriaTemaService;

    @Test
    @DisplayName("Deve retornar tema existente por ID")
    void deveRetornarTemaExistentePorId() {
        TemaProduto tema = new TemaProduto();
        tema.setId(1);
        tema.setDescricao("Tema Teste");

        when(repository.findById(1)).thenReturn(Optional.of(tema));

        TemaProduto resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("Tema Teste", resultado.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar tema inexistente por ID")
    void deveRetornarNullAoBuscarTemaInexistentePorId() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        TemaProduto resultado = service.findById(999);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve listar temas corretamente")
    void deveListarTemasCorretamente() {
        TemaProduto tema = new TemaProduto();
        tema.setId(1);
        tema.setDescricao("Tema Teste");

        Page<TemaProduto> paginaSimulada = new PageImpl<>(List.of(tema));

        when(repository.filtrar(eq("teste"), eq("categoria"), eq(true), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<TemaProduto> resultado = service.get("teste", "categoria", 0, true);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Tema Teste", resultado.getContent().get(0).getDescricao());
    }

    @Test
    @DisplayName("Deve salvar tema corretamente quando categoria existe")
    void deveSalvarTemaCorretamenteQuandoCategoriaExiste() {
        CategoriaTema categoria = new CategoriaTema();
        categoria.setId(1);
        categoria.setTitulo("Categoria Teste");

        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Descricao", 1);

        TemaProduto tema = new TemaProduto();
        tema.setId(1);
        tema.setDescricao("Descricao");
        tema.setCategoriaTema(categoria);

        when(categoriaTemaService.findById(1)).thenReturn(categoria);
        when(repository.save(any(TemaProduto.class))).thenReturn(tema);

        TemaProduto resultado = service.store(dto);

        assertNotNull(resultado);
        assertEquals("Descricao", resultado.getDescricao());
        assertEquals("Categoria Teste", resultado.getCategoriaTema().getTitulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar tema com categoria inexistente")
    void deveLancarExcecaoAoSalvarTemaComCategoriaInexistente() {
        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Descricao",  999);

        when(categoriaTemaService.findById(999)).thenReturn(null);

        assertThrows(
                CategoriaTemaNaoEncontradaException.class,
                () -> service.store(dto)
        );
    }

    @Test
    @DisplayName("Deve atualizar tema existente corretamente")
    void deveAtualizarTemaExistenteCorretamente() {
        CategoriaTema categoria = new CategoriaTema();
        categoria.setId(1);
        categoria.setTitulo("Categoria Teste");

        TemaProduto existente = new TemaProduto();
        existente.setId(1);
        existente.setDescricao("Antigo");
        existente.setAtivo(true);

        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Novo",  1);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(categoriaTemaService.findById(1)).thenReturn(categoria);
        when(repository.save(any(TemaProduto.class))).thenReturn(existente);

        TemaProduto resultado = service.update(dto, 1);

        assertNotNull(resultado);
        assertEquals("Novo", existente.getDescricao());
        assertEquals("Categoria Teste", existente.getCategoriaTema().getTitulo());
        assertNotNull(existente.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar tema inexistente")
    void deveLancarExcecaoAoAtualizarTemaInexistente() {
        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Novo",  1);

        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                TemaProdutoNaoEncontradoException.class,
                () -> service.update(dto, 999)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar tema com categoria inexistente")
    void deveLancarExcecaoAoAtualizarTemaComCategoriaInexistente() {
        TemaProduto existente = new TemaProduto();
        existente.setId(1);
        existente.setDescricao("Antigo");

        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Novo",  999);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(categoriaTemaService.findById(999)).thenReturn(null);

        assertThrows(
                CategoriaTemaNaoEncontradaException.class,
                () -> service.update(dto, 1)
        );
    }

    @Test
    @DisplayName("Deve mudar estado de ativo para inativo")
    void deveMudarEstadoDeAtivoParaInativo() {
        TemaProduto existente = new TemaProduto();
        existente.setId(1);
        existente.setDescricao("Teste");
        existente.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(TemaProduto.class))).thenReturn(existente);

        service.mudarEstado(1);

        assertFalse(existente.getAtivo());
    }

    @Test
    @DisplayName("Deve mudar estado de inativo para ativo")
    void deveMudarEstadoDeInativoParaAtivo() {
        TemaProduto existente = new TemaProduto();
        existente.setId(1);
        existente.setDescricao("Teste");
        existente.setAtivo(false);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(TemaProduto.class))).thenReturn(existente);

        service.mudarEstado(1);

        assertTrue(existente.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar estado de tema inexistente")
    void deveLancarExcecaoAoMudarEstadoDeTemaInexistente() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                TemaProdutoNaoEncontradoException.class,
                () -> service.mudarEstado(999)
        );
    }
}