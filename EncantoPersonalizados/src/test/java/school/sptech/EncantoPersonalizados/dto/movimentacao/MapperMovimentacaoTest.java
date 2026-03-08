package school.sptech.EncantoPersonalizados.dto.movimentacao;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.entities.Contraparte;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MapperMovimentacaoTest {

    @Test
    void toEntity_mapsFields() {
        RequestMovimentacaoDTO dto = new RequestMovimentacaoDTO(
                "ENTRADA",
                "Venda de produto",
                150.0,
                "PAGO",
                LocalDate.of(2026, 3, 6),
                null,
                1,
                2
        );

        Contraparte contraparte = new Contraparte();
        contraparte.setId(1);
        contraparte.setNome("Fornecedor X");

        CategoriaMovimentacao cat = new CategoriaMovimentacao();
        cat.setId(2);
        cat.setDescricao("Vendas");

        Movimentacao entity = MapperMovimentacao.toEntity(dto, contraparte, cat);

        assertNotNull(entity);
        assertEquals("ENTRADA", entity.getTipo());
        assertEquals("Venda de produto", entity.getDescricao());
        assertEquals(150.0, entity.getValor());
        assertEquals("PAGO", entity.getStatusPagamento());
        assertEquals(LocalDate.of(2026, 3, 6), entity.getDataVencimento());
        assertEquals(contraparte, entity.getContraparte());
        assertEquals(cat, entity.getCategoriaMovimentacao());
        assertTrue(entity.getStatus());
    }

    @Test
    void toDto_mapsFields() {
        Movimentacao m = new Movimentacao();
        m.setTipo("SAIDA");
        m.setDescricao("Compra de insumos");
        m.setValor(75.5);
        m.setStatusPagamento("PENDENTE");
        m.setDataVencimento(LocalDate.of(2026, 4, 1));
        m.setDataPagamento(LocalDate.of(2026, 4, 2));

        Contraparte c = new Contraparte();
        c.setId(5);
        c.setNome("Parceiro");
        c.setDescricao("Desc");
        m.setContraparte(c);

        CategoriaMovimentacao cat = new CategoriaMovimentacao();
        cat.setId(6);
        cat.setDescricao("Custos");
        m.setCategoriaMovimentacao(cat);

        ResponseMovimentacaoDTO dto = MapperMovimentacao.toDto(m);

        assertNotNull(dto);
        assertEquals("SAIDA", dto.tipo());
        assertEquals("Compra de insumos", dto.descricao());
        assertEquals(75.5, dto.valor());
        assertEquals("PENDENTE", dto.statusPagamento());
        assertEquals(LocalDate.of(2026, 4, 1), dto.dataVencimento());
        assertEquals(LocalDate.of(2026, 4, 2), dto.dataPagamento());
        assertNotNull(dto.categoria());
        assertNotNull(dto.contraparte());
        assertEquals("Custos", dto.categoria().descricao());
        assertEquals("Parceiro", dto.contraparte().nome());
    }
}

