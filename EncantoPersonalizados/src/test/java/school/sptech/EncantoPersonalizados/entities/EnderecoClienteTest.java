package school.sptech.EncantoPersonalizados.entities;

import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoClienteTest {

    @Test
    @DisplayName("Entities - EnderecoCliente - deve setar campos básicos")
    void shouldSetBasicFields() {
        EnderecoCliente e = new EnderecoCliente();
        e.setLogradouro("Rua A");
        e.setCidade("Cidade");
        e.setCep("12345-678");

        assertEquals("Rua A", e.getLogradouro());
        assertEquals("Cidade", e.getCidade());
        assertEquals("12345-678", e.getCep());
        assertTrue(e.getAtivo());
    }
}

