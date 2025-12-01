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
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoReordenacaoDto;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;
import school.sptech.EncantoPersonalizados.repository.StatusPedidoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusPedidoServiceTest {

    @InjectMocks
    private StatusPedidoService service;

    @Mock
    private StatusPedidoRepository repository;

    @Test
    @DisplayName("Deve listar status pedidos ativos corretamente")
    void deveListarStatusPedidosAtivos() {
        StatusPedido status = new StatusPedido();
        status.setId(1);
        status.setStatus("Em andamento");
        status.setAtivo(true);

        Page<StatusPedido> paginaSimulada = new PageImpl<>(List.of(status));

        when(repository.filtrar(eq(true), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<StatusPedido> resultado = service.listar(0, true);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Em andamento", resultado.getContent().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve salvar status pedido corretamente")
    void deveSalvarStatusPedidoCorretamente() {
        StatusPedido status = new StatusPedido();
        status.setStatus("Novo");
        status.setCor("Azul");

        when(repository.save(any(StatusPedido.class))).thenReturn(status);

        StatusPedido resultado = service.store(status);

        assertNotNull(resultado);
        assertEquals("Novo", resultado.getStatus());
        assertEquals("Azul", resultado.getCor());
        assertNotNull(resultado.getCreatedAt());
    }

    @Test
    @DisplayName("Deve atualizar status pedido existente")
    void deveAtualizarStatusPedidoExistente() {
        StatusPedido existente = new StatusPedido();
        existente.setId(1);
        existente.setStatus("Antigo");
        existente.setCor("Vermelho");
        existente.setOrdemKanban(1);

        StatusPedido novo = new StatusPedido();
        novo.setStatus("Atualizado");
        novo.setCor("Verde");
        novo.setOrdemKanban(2);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(StatusPedido.class))).thenReturn(existente);

        StatusPedido resultado = service.update(novo, 1);

        assertNotNull(resultado);
        assertEquals("Atualizado", resultado.getStatus());
        assertEquals("Verde", resultado.getCor());
        assertEquals(2, resultado.getOrdemKanban());
        assertNotNull(resultado.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar status inexistente")
    void deveLancarExcecaoAoAtualizarStatusInexistente() {
        StatusPedido novo = new StatusPedido();
        novo.setStatus("Atualizado");

        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> service.update(novo, 999)
        );
    }

    @Test
    @DisplayName("Deve mudar estado de ativo para inativo e vice-versa")
    void deveMudarEstadoDeAtivoParaInativo() {
        StatusPedido existente = new StatusPedido();
        existente.setId(1);
        existente.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(existente));

        service.mudarEstado(1);

        assertFalse(existente.isAtivo());
    }

    @Test
    @DisplayName("Deve reordenar kanban corretamente")
    void deveReordenarKanbanCorretamente() {
        StatusPedido existente = new StatusPedido();
        existente.setId(1);
        existente.setOrdemKanban(1);

        StatusPedidoReordenacaoDto dto = new StatusPedidoReordenacaoDto(1, 5);

        when(repository.findById(1)).thenReturn(Optional.of(existente));

        service.reordenarKanban(List.of(dto));

        assertEquals(5, existente.getOrdemKanban());
    }

    @Test
    @DisplayName("Deve retornar status pedido existente por ID")
    void deveRetornarStatusPedidoExistentePorId() {
        StatusPedido existente = new StatusPedido();
        existente.setId(1);
        existente.setStatus("Teste");

        when(repository.findById(1)).thenReturn(Optional.of(existente));

        StatusPedido resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getStatus());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar status inexistente por ID")
    void deveRetornarNullAoBuscarStatusInexistentePorId() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        StatusPedido resultado = service.findById(999);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve retornar primeiro status do kanban")
    void deveRetornarPrimeiroStatusDoKanban() {
        StatusPedido primeiro = new StatusPedido();
        primeiro.setId(1);
        primeiro.setOrdemKanban(1);

        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(primeiro));

        StatusPedido resultado = service.findFirstOfKanbanOrder();

        assertNotNull(resultado);
        assertEquals(1, resultado.getOrdemKanban());
    }

    @Test
    @DisplayName("Deve retornar null quando não houver status no kanban")
    void deveRetornarNullQuandoNaoHouverStatusNoKanban() {
        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.empty());

        StatusPedido resultado = service.findFirstOfKanbanOrder();

        assertNull(resultado);
    }
}