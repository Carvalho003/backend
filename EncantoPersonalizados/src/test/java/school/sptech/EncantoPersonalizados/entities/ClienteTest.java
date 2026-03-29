package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    @DisplayName("Entities - Cliente - deve setar nome e telefone")
    void shouldSetNomeAndTelefone() {
        Cliente c = new Cliente();
        c.setNome("Cliente A");
        c.setTelefone("5511999999999");

        assertEquals("Cliente A", c.getNome());
        assertEquals("5511999999999", c.getTelefone());
        assertNull(c.getEnderecoClientes());
    }
}

