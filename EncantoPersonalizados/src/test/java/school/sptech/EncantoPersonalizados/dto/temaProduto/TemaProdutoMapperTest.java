package school.sptech.EncantoPersonalizados.dto.temaProduto;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemaProdutoMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(TemaProdutoMapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        TemaProdutoRequestDTO dto = new TemaProdutoRequestDTO("Barbie", 1);
        TemaProduto entity = TemaProdutoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Barbie", entity.getDescricao());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    void toDto_null_returnsNull() {
        assertNull(TemaProdutoMapper.toDto((TemaProduto) null));
    }

    @Test
    void toDto_mapsFields() {
        TemaProduto entity = new TemaProduto();
        entity.setId(7);
        entity.setDescricao("X");
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now.plusDays(2));

        TemaProdutoResponseDTO dto = TemaProdutoMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(7, dto.id());
        assertEquals("X", dto.descricao());
        assertEquals(now, dto.created_at());
        assertEquals(now.plusDays(2), dto.updated_at());
    }

    @Test
    void toDto_list_empty_returnsNull() {
        assertNull(TemaProdutoMapper.toDto(List.of()));
    }
}
