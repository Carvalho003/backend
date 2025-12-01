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
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaMapper;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.CategoriaTemaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaTemaServiceTest {

    @InjectMocks
    private CategoriaTemaService service;

    @Mock
    private CategoriaTemaRepository repository;

    @Test
    @DisplayName("Deve criar categoria de tema corretamente")
    void deveCriarCategoriaTemaCorretamente() {
        CategoriaTemaRequestDTO dto = new CategoriaTemaRequestDTO("Tema Teste");

        CategoriaTema entity = CategoriaTemaMapper.toEntity(dto);
        entity.setId(1);

        when(repository.save(any(CategoriaTema.class))).thenReturn(entity);

        CategoriaTemaResponseDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        assertEquals("Tema Teste", resultado.titulo());
        assertEquals(1, resultado.id());
    }

    @Test
    @DisplayName("Deve atualizar categoria de tema existente")
    void deveAtualizarCategoriaTemaExistente() {
        CategoriaTemaRequestDTO dto = new CategoriaTemaRequestDTO("Tema Atualizado");

        CategoriaTema existente = new CategoriaTema();
        existente.setId(1);
        existente.setTitulo("Antigo");
        existente.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(CategoriaTema.class))).thenReturn(CategoriaTemaMapper.toEntity(dto));

        CategoriaTemaResponseDTO resultado = service.update(dto, 1);

        assertNotNull(resultado);
        assertEquals("Tema Atualizado", resultado.titulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar categoria inexistente")
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        CategoriaTemaRequestDTO dto = new CategoriaTemaRequestDTO("Tema");

        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                CategoriaTemaNaoEncontradaException.class,
                () -> service.update(dto, 999)
        );
    }

    @Test
    @DisplayName("Deve listar categorias de tema corretamente")
    void deveListarCategoriasDeTemaCorretamente() {
        CategoriaTema entity = new CategoriaTema();
        entity.setId(1);
        entity.setTitulo("Tema Teste");
        entity.setAtivo(true);

        Page<CategoriaTema> paginaSimulada = new PageImpl<>(List.of(entity));

        when(repository.filtrar(eq("Tema"), eq(true), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<CategoriaTema> resultado = service.listar("Tema", true, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Tema Teste", resultado.getContent().get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve retornar categoria existente por ID")
    void deveRetornarCategoriaExistentePorId() {
        CategoriaTema entity = new CategoriaTema();
        entity.setId(1);
        entity.setTitulo("Tema Teste");

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        CategoriaTema resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("Tema Teste", resultado.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar categoria inexistente por ID")
    void deveRetornarNullAoBuscarCategoriaInexistentePorId() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        CategoriaTema resultado = service.findById(999);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve mudar estado de ativo para inativo")
    void deveMudarEstadoDeAtivoParaInativo() {
        CategoriaTema entity = new CategoriaTema();
        entity.setId(1);
        entity.setTitulo("Tema Teste");
        entity.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(any(CategoriaTema.class))).thenReturn(entity);

        service.mudarEstado(1);

        assertFalse(entity.getAtivo());
    }

    @Test
    @DisplayName("Deve mudar estado de inativo para ativo")
    void deveMudarEstadoDeInativoParaAtivo() {
        CategoriaTema entity = new CategoriaTema();
        entity.setId(1);
        entity.setTitulo("Tema Teste");
        entity.setAtivo(false);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(any(CategoriaTema.class))).thenReturn(entity);

        service.mudarEstado(1);

        assertTrue(entity.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar estado de categoria inexistente")
    void deveLancarExcecaoAoMudarEstadoDeCategoriaInexistente() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                CategoriaTemaNaoEncontradaException.class,
                () -> service.mudarEstado(999)
        );
    }
}