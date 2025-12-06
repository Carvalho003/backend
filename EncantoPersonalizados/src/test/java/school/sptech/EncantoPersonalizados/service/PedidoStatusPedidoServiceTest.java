package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;
import school.sptech.EncantoPersonalizados.repository.PedidoStatusPedidoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoStatusPedidoServiceTest {
    @Mock
    PedidoStatusPedidoRepository repository;

    @Mock
    PedidoService pedidoService;

    @Mock
    StatusPedidoService statusPedidoService;

    @InjectMocks
    PedidoStatusPedidoService service;

    @Test
    @DisplayName("Deve lançar exceção quando o StatusPedido não existe")
    void LancarExcecaoQuandoStatusPedidoNaoExistir() {
        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();
        StatusPedido status = new StatusPedido();
        status.setId(1);
        pedidoStatusPedido.setStatus(status);

        when(statusPedidoService.findById(1)).thenReturn(null);

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> service.salvar(pedidoStatusPedido)
        );

        assertEquals("StatusPedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Pedido não existe")
    void LancarExcecaoQuandoPedidoNaoExistir() {
        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();

        StatusPedido status = new StatusPedido();
        status.setId(1);
        pedidoStatusPedido.setStatus(status);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedidoStatusPedido.setPedido(pedido);

        when(statusPedidoService.findById(1)).thenReturn(status);
        when(pedidoService.findById(1)).thenReturn(null);

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> service.salvar(pedidoStatusPedido)
        );

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve salvar o novo status corretamente")
    void deveSalvarNovoStatusCorretamente() {
        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();

        StatusPedido status = new StatusPedido();
        status.setId(1);
        pedidoStatusPedido.setStatus(status);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedidoStatusPedido.setPedido(pedido);

        when(statusPedidoService.findById(1)).thenReturn(status);
        when(pedidoService.findById(1)).thenReturn(pedido);

        PedidoStatusPedido statusAntigo = new PedidoStatusPedido();
        statusAntigo.setStatusAtual(true);
        List<PedidoStatusPedido> statusAtuais = List.of(statusAntigo);
        when(repository.findStatusAtualByPedidoId(1)).thenReturn(statusAtuais);

        when(repository.save(statusAntigo)).thenReturn(statusAntigo);

        PedidoStatusPedido pedidoStatusPedidoSalvo = new PedidoStatusPedido();
        pedidoStatusPedidoSalvo.setStatus(status);
        pedidoStatusPedidoSalvo.setPedido(pedido);
        when(repository.save(pedidoStatusPedido)).thenReturn(pedidoStatusPedidoSalvo);

        PedidoStatusPedido resultado = service.salvar(pedidoStatusPedido);

        assertNotNull(resultado);
        assertFalse(statusAntigo.isStatusAtual());
        verify(repository).save(statusAntigo);

        verify(repository , times(1)).save(pedidoStatusPedido);
    }

    @Test
    @DisplayName("Quando buscar por ID que não existe retornar NULL")
    void RetornarNullSeIdNaoExistirTest() {
        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();
        pedidoStatusPedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.empty());

        PedidoStatusPedido resultado = service.findById(1);

        assertNull(resultado);

    }

    @Test
    @DisplayName("Quando buscar por ID existente deve retornar PedidoStatusPedido com esse ID")
    void quandoBuscarPorIdQueExisteRetornarProdutoPedidoCorrespondenteTest() {
        PedidoStatusPedido PedidoStatusPedido = new PedidoStatusPedido();
        PedidoStatusPedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(PedidoStatusPedido));

        PedidoStatusPedido resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals(1, PedidoStatusPedido.getId());

    }
}