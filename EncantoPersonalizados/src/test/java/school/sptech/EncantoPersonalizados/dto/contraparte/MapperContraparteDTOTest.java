package school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;

import static org.junit.jupiter.api.Assertions.*;

class MapperContraparteDTOTest {

    @Test
    void toEntity_mapsFields() {
        RequestContraparteDTO dto = new RequestContraparteDTO("Nome","Desc","Seg","Tipo");
        var entity = MapperContraparteDTO.toEntity(dto);
        assertNotNull(entity);
        assertEquals("Nome", entity.getNome());
        assertEquals("Desc", entity.getDescricao());
    }

    @Test
    void toDto_mapsFields() {
        Contraparte c = new Contraparte();
        c.setNome("N");
        c.setDescricao("D");
        c.setSegmento("S");
        c.setTipoContrato("T");

        var dto = MapperContraparteDTO.toDto(c);
        assertNotNull(dto);
        assertEquals("N", dto.nome());
        assertEquals("D", dto.descricao());
    }
}

