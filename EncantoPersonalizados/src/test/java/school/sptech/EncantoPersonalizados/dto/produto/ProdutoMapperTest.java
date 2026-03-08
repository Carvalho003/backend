package school.sptech.EncantoPersonalizados.dto.produto;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.FotoProduto;
import school.sptech.EncantoPersonalizados.entities.ItemProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(ProdutoMapper.toEntity(null));
    }

    @Test
    void toDto_mapsFields() {
        Produto p = new Produto();
        p.setId(12);
        p.setTitulo("T");
        p.setDescricao("D");

        TemaProduto tema = new TemaProduto();
        tema.setId(2);
        p.setTemaProduto(tema);

        ItemProduto item = new ItemProduto();
        item.setId(3);
        p.setItemProduto(item);

        FotoProduto f = new FotoProduto();
        f.setId(5);
        f.setFoto("img");

        p.setFotoProdutos(List.of(f));
        LocalDateTime now = LocalDateTime.now();
        p.setCreatedAt(now);
        p.setUpdatedAt(now.plusDays(1));

        var dto = ProdutoMapper.toDto(p);
        assertNotNull(dto);
        assertEquals(12, dto.id());
        assertEquals("T", dto.titulo());
        assertNotNull(dto.tema());
        assertNotNull(dto.item());
        assertEquals(1, dto.fotos().size());
    }

    @Test
    void toDto_list_null_returnsNull() {
        assertNull(ProdutoMapper.toDto((List<Produto>) null));
    }
}
