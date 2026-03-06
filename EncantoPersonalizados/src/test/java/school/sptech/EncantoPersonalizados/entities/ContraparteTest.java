package school.sptech.EncantoPersonalizados.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContraparteTest {

    @Test
    @DisplayName("Entities - Contraparte - deve setar campos e aplicar onCreate para datas e status")
    void shouldSetFieldsAndOnCreate() {
        Contraparte c = new Contraparte();
        c.setNome("Nome");
        c.setDescricao("Descricao");
        c.setSegmento("Seg");
        c.setTipoContrato("Tipo");
        // executar manualmente o método de lifecycle para testar comportamento
        c.onCreate();

        assertEquals("Nome", c.getNome());
        assertEquals("Descricao", c.getDescricao());
        assertNotNull(c.getCreatedAt());
        assertNotNull(c.getUpdatedAt());
        assertTrue(c.getStatus());
    }
}
