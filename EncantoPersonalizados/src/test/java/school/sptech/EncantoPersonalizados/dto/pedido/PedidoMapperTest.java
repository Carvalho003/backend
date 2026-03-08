package school.sptech.EncantoPersonalizados.dto.pedido;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.ProdutoPedido;
import school.sptech.EncantoPersonalizados.entities.Usuario;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoMapperTest {

    @Test
    void toEntity_mapsFields() {
        PedidoRequestDto req = new PedidoRequestDto("Obs","Web", 1, 2, List.of());
        var entity = PedidoMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("Obs", entity.getObservacoes());
        assertEquals("Web", entity.getOrigem());
    }

    @Test
    void toDto_mapsFields() {
        Pedido pedido = new Pedido();
        pedido.setId(15);
        pedido.setObservacoes("Observ");
        pedido.setOrigem("Loja");
        pedido.setCreatedAt(LocalDateTime.now());
        pedido.setUpdatedAt(LocalDateTime.now());

        Cliente cliente = new Cliente();
        cliente.setId(3);
        cliente.setNome("Cli");
        cliente.setTelefone("111");
        cliente.setEnderecoClientes(List.of()); // evitar NPE no ClienteMapper
        pedido.setCliente(cliente);

        Usuario usuario = new Usuario();
        usuario.setId(4);
        usuario.setName("Usr");
        pedido.setUsuario(usuario);

        pedido.setProdutoPedidos(List.of());
        pedido.setPedidoStatusPedidos(List.of());

        PedidoResponseDto dto = PedidoMapper.toDto(pedido, null);
        assertNotNull(dto);
        assertEquals(15, dto.id());
        assertEquals("Observ", dto.observacoes());
        assertEquals("Loja", dto.origem());
        assertNotNull(dto.cliente());
        assertNotNull(dto.usuario());
        assertEquals(0, dto.produtos().size());
    }
}

