package school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemProdutoMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(ItemProdutoMapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        ItemProdutoRequestDTO dto = new ItemProdutoRequestDTO(
                "desc", 10.0, 5.0, 2, 10.0, 5.0, 200.0, 15.0, "mat", "8.0", 8.0
        );
        ItemProduto entity = ItemProdutoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("desc", entity.getDescricao());
        assertEquals(10.0, entity.getPrecoVenda());
    }

    @Test
    void toDto_null_returnsNull() {
        assertNull(ItemProdutoMapper.toDto((ItemProduto) null));
    }

    @Test
    void toDto_mapsFields() {
        ItemProduto entity = new ItemProduto();
        entity.setId(3);
        entity.setDescricao("abc");
        entity.setPrecoVenda(12.5);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now.plusHours(1));

        ItemProdutoResponseDTO dto = ItemProdutoMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(3, dto.id());
        assertEquals("abc", dto.descricao());
        assertEquals(12.5, dto.precoVenda());
    }
}

