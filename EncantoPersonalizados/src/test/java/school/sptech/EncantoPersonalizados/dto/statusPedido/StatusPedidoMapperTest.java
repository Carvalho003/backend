package school.sptech.EncantoPersonalizados.dto.statusPedido;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;

import static org.junit.jupiter.api.Assertions.*;

class StatusPedidoMapperTest {

    @Test
    void toEntity_mapsFields() {
        StatusPedidoRequestDto dto = new StatusPedidoRequestDto("Pronto", "#fff", 1);
        StatusPedido entity = StatusPedidoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Pronto", entity.getStatus());
        assertEquals("#fff", entity.getCor());
        assertEquals(1, entity.getOrdemKanban());
    }

    @Test
    void toResponseDto_mapsFields() {
        StatusPedido status = new StatusPedido();
        status.setId(4);
        status.setStatus("S");
        status.setCor("C");
        status.setOrdemKanban(2);

        StatusPedidoResponseDto dto = StatusPedidoMapper.toResponseDto(status);
        assertNotNull(dto);
        assertEquals(4, dto.id());
        assertEquals("S", dto.status());
    }
}

