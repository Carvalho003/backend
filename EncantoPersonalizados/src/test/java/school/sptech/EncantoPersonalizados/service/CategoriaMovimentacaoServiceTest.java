package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import school.sptech.EncantoPersonalizados.core.application.usecase.categoriaMovimentacao.CategoriaMovimentacaoUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaMovimentacaoGateway;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.CategoriaMovimentacaoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaMovimentacaoUseCaseImplTest {

    @Mock
    private CategoriaMovimentacaoGateway repository;

    @InjectMocks
    private CategoriaMovimentacaoUseCaseImpl service;

    @Test
    void deveCriarCategoriaComSucesso() {
        RequestCategoriaMovimentacaoDTO dto = new RequestCategoriaMovimentacaoDTO("Transporte");

        when(repository.existsByDescricao("Transporte")).thenReturn(false);

        CategoriaMovimentacao entity = new CategoriaMovimentacao();
        entity.setId(1);
        entity.setDescricao("Transporte");
        entity.setStatus(true);

        when(repository.save(any(CategoriaMovimentacao.class))).thenReturn(entity);

        ResponseCategoriaMovimentacaoDTO response = service.create(dto);

        assertEquals(1, response.id());
        assertEquals("Transporte", response.descricao());
        verify(repository).save(any());
    }

    @Test
    void deveLancarErroAoCriarCategoriaDuplicada() {
        RequestCategoriaMovimentacaoDTO dto = new RequestCategoriaMovimentacaoDTO("Alimentação");

        when(repository.existsByDescricao("Alimentação")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Já existe uma categoria com essa descrição", ex.getMessage());
    }

    @Test
    void deveRetornarPaginacaoDeCategorias() {
        Page<CategoriaMovimentacao> page = new PageImpl<>(
                List.of(mock(CategoriaMovimentacao.class))
        );

        when(repository.filtrar(null, true, PageRequest.of(0, 10))).thenReturn(page);

        Page<CategoriaMovimentacao> result = service.get("", true, 0);

        assertEquals(1, result.getContent().size());
        verify(repository).filtrar(null, true, PageRequest.of(0, 10));
    }

    @Test
    void deveRetornarCategoriaPorId() {
        CategoriaMovimentacao categoria = new CategoriaMovimentacao();
        categoria.setId(5);
        categoria.setDescricao("Saúde");
        categoria.setStatus(true);

        when(repository.findByIdAndStatusTrue(5)).thenReturn(Optional.of(categoria));

        ResponseCategoriaMovimentacaoDTO dto = service.findById(5);

        assertEquals(5, dto.id());
        assertEquals("Saúde", dto.descricao());
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoEncontradaEmFindById() {
        when(repository.findByIdAndStatusTrue(10)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(10));
        assertEquals("Categoria não encontrada", ex.getMessage());
    }


    @Test
    void deveAtualizarCategoriaComSucesso() {
        CategoriaMovimentacao categoria = new CategoriaMovimentacao();
        categoria.setId(3);
        categoria.setDescricao("Antiga");

        RequestCategoriaMovimentacaoDTO dto = new RequestCategoriaMovimentacaoDTO("Nova");

        when(repository.findByIdAndStatusTrue(3)).thenReturn(Optional.of(categoria));
        when(repository.save(categoria)).thenReturn(categoria);

        ResponseCategoriaMovimentacaoDTO response = service.update(3, dto);

        assertEquals("Nova", response.descricao());
        verify(repository).save(categoria);
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoEncontradaEmUpdate() {
        RequestCategoriaMovimentacaoDTO dto = new RequestCategoriaMovimentacaoDTO("Teste");

        when(repository.findByIdAndStatusTrue(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(99, dto));
        assertEquals("Categoria não encontrada", ex.getMessage());
    }

    @Test
    void deveDeletarCategoriaComSucesso() {
        CategoriaMovimentacao categoria = new CategoriaMovimentacao();
        categoria.setId(7);
        categoria.setDescricao("Lazer");
        categoria.setStatus(true);

        when(repository.findById(7)).thenReturn(Optional.of(categoria));
        when(repository.save(categoria)).thenReturn(categoria);

        assertDoesNotThrow(() -> service.delete(7));
        assertFalse(categoria.getStatus()); // foi desativado
        verify(repository).save(categoria);
    }

    @Test
    void deveLancarErroAoTentarDeletarCategoriaInexistente() {
        when(repository.findById(55)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.delete(55));
        assertEquals("Categoria não encontrada", ex.getMessage());
    }
}
