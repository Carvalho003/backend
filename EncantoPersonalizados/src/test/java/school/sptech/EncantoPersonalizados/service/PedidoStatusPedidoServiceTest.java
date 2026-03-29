package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.core.application.usecase.pedidoStatusPedido.PedidoStatusPedidoUseCaseImpl;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoStatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoStatusPedidoUseCaseImplTest {
    @Mock
    PedidoStatusPedidoGateway repository;

    @Mock
    PedidoGateway pedidoGateway;

    @Mock
    StatusPedidoGateway statusPedidoGateway;

    @InjectMocks
    PedidoStatusPedidoUseCaseImpl service;

    @Test
    @DisplayName("Deve lançar exceção quando o StatusPedido não existe")
    void LancarExcecaoQuandoStatusPedidoNaoExistir() {
        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();
        StatusPedido status = new StatusPedido();
        status.setId(1);
        pedidoStatusPedido.setStatus(status);

        when(statusPedidoGateway.findById(1)).thenReturn(Optional.empty());

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

        when(statusPedidoGateway.findById(1)).thenReturn(Optional.of(status));
        when(pedidoGateway.findById(1)).thenReturn(Optional.empty());

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

        when(statusPedidoGateway.findById(1)).thenReturn(Optional.of(status));
        when(pedidoGateway.findById(1)).thenReturn(Optional.of(pedido));

        PedidoStatusPedido statusAntigo = new PedidoStatusPedido();
        statusAntigo.setStatusAtual(true);
        List<PedidoStatusPedido> statusAtuais = List.of(statusAntigo);
        when(repository.findStatusAtualByPedidoId(1)).thenReturn(statusAtuais);

        when(repository.salvar(statusAntigo)).thenReturn(statusAntigo);

        PedidoStatusPedido pedidoStatusPedidoSalvo = new PedidoStatusPedido();
        pedidoStatusPedidoSalvo.setStatus(status);
        pedidoStatusPedidoSalvo.setPedido(pedido);
        when(repository.salvar(pedidoStatusPedido)).thenReturn(pedidoStatusPedidoSalvo);

        PedidoStatusPedido resultado = service.salvar(pedidoStatusPedido);

        assertNotNull(resultado);
        assertFalse(statusAntigo.isStatusAtual());
        verify(repository).salvar(statusAntigo);

        verify(repository , times(1)).salvar(pedidoStatusPedido);
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
