package school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FotoProdutoMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(FotoProdutoMapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        FotoProdutoRequestDTO dto = new FotoProdutoRequestDTO("base64image");
        FotoProduto entity = FotoProdutoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("base64image", entity.getFoto());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    void toDto_null_returnsNull() {
        assertNull(FotoProdutoMapper.toDto((FotoProduto) null));
    }

    @Test
    void toDto_mapsFields() {
        FotoProduto entity = new FotoProduto();
        entity.setId(5);
        entity.setFoto("img");
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now.plusDays(1));

        FotoProdutoResponseDTO dto = FotoProdutoMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(5, dto.id());
        assertEquals("img", dto.foto());
        assertEquals(now, dto.created_at());
        assertEquals(now.plusDays(1), dto.updated_at());
    }
}
