package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoTest {

    @Test
    @DisplayName("Entities - Movimentacao - deve setar campos basicos e referencia a categoria e contraparte")
    void shouldSetFieldsAndRelations() {
        Movimentacao m = new Movimentacao();
        m.setTipo("Entrada");
        m.setDescricao("Pagamento");
        m.setValor(100.0);

        CategoriaMovimentacao cm = new CategoriaMovimentacao();
        cm.setDescricao("Receita");
        m.setCategoriaMovimentacao(cm);

        Contraparte c = new Contraparte();
        c.setNome("Fornecedor");
        m.setContraparte(c);

        assertEquals("Entrada", m.getTipo());
        assertEquals("Pagamento", m.getDescricao());
        assertEquals(100.0, m.getValor());
        assertEquals(cm, m.getCategoriaMovimentacao());
        assertEquals(c, m.getContraparte());
    }
}

