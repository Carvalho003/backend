package school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoStatusPedidoMapperTest {

    @Test
    void toResponseDto_mapsFields() {
        PedidoStatusPedido psp = new PedidoStatusPedido();
        psp.setId(20);

        Pedido pedido = new Pedido();
        pedido.setId(2);
        psp.setPedido(pedido);

        StatusPedido status = new StatusPedido();
        status.setId(3);
        psp.setStatus(status);

        psp.setStatusAtual(true);
        LocalDateTime now = LocalDateTime.now();
        psp.setCreatedAt(now);
        psp.setUpdatedAt(now.plusHours(1));

        var dto = PedidoStatusPedidoMapper.toResponseDto(psp);

        assertNotNull(dto);
        assertEquals(20, dto.id());
        assertEquals(2, dto.idPedido());
        assertEquals(3, dto.idStatusPedido());
        assertTrue(dto.statusAtual());
        assertEquals(now, dto.createdAt());
    }

    @Test
    void toResponseDtoList_mapsList() {
        PedidoStatusPedido p1 = new PedidoStatusPedido();
        p1.setId(1);
        Pedido ped1 = new Pedido(); ped1.setId(5); p1.setPedido(ped1);
        StatusPedido s1 = new StatusPedido(); s1.setId(7); p1.setStatus(s1);

        List<PedidoStatusPedido> list = List.of(p1);
        var dtoList = PedidoStatusPedidoMapper.toResponseDtoList(list);
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals(1, dtoList.get(0).id());
    }
}

