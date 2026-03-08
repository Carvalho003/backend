package school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;

import static org.junit.jupiter.api.Assertions.*;

class MapperCategoriaMovimentacaoTest {

    @Test
    void toEntity_mapsFields() {
        RequestCategoriaMovimentacaoDTO dto = new RequestCategoriaMovimentacaoDTO("Desc");
        var entity = MapperCategoriaMovimentacao.toEntity(dto);
        assertNotNull(entity);
        assertEquals("Desc", entity.getDescricao());
    }

    @Test
    void toDTO_mapsFields() {
        CategoriaMovimentacao c = new CategoriaMovimentacao();
        c.setId(1);
        c.setDescricao("D");
        var dto = MapperCategoriaMovimentacao.toDTO(c);
        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("D", dto.descricao());
    }
}

