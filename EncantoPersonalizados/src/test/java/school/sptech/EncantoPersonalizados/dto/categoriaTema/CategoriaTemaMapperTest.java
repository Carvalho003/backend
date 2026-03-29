package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTemaMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(CategoriaTemaMapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        CategoriaTemaRequestDTO dto = new CategoriaTemaRequestDTO("Titulo");
        CategoriaTema entity = CategoriaTemaMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Titulo", entity.getTitulo());
    }

    @Test
    void toDto_null_returnsNull() {
        assertNull(CategoriaTemaMapper.toDto((CategoriaTema) null));
    }

    @Test
    void toDto_mapsWithTemas() {
        CategoriaTema entity = new CategoriaTema();
        entity.setId(2);
        entity.setTitulo("c");
        TemaProduto t = new TemaProduto();
        t.setId(11);
        t.setDescricao("d");
        t.setCreatedAt(LocalDateTime.now());
        entity.setTemaProdutos(List.of(t));

        CategoriaTemaResponseDTO dto = CategoriaTemaMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(2, dto.id());
        assertEquals(1, dto.temas().size());
        assertEquals(11, dto.temas().get(0).id());
    }

    @Test
    void toDto_list_null_returnsNull() {
        assertNull(CategoriaTemaMapper.toDto((List<CategoriaTema>) null));
    }
}

