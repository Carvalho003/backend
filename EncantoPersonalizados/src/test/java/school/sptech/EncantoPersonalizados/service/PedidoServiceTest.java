package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.repository.PedidoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock
    PedidoRepository repository;

    @InjectMocks
    PedidoService service;

    @Test
    @DisplayName("Quando buscar por ID que não existe retornar NULL")
    void RetornarNullSeIdNaoExistirTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.empty());

        Pedido resultado = service.findById(1);

        assertNull(resultado);

    }

    @Test
    @DisplayName("Quando buscar por ID existente deve retornar Pedido com esse ID")
    void quandoBuscarPorIdQueExisteRetornarPedidoCorrespondenteTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(pedido));

        Pedido resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals(1, pedido.getId());

    }
}