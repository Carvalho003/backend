package school.sptech.EncantoPersonalizados.dto.cliente;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(ClienteMapper.toEntity(null));
    }

    @Test
    void endereco_toEntity_mapsFields() {
        var dto = new EnderecoClienteRequestDTO("Log", "10", "Bair", "12345", "UF", "City", "State", "Mun", "Comp");
        Cliente cliente = new Cliente();
        cliente.setId(1);

        var end = ClienteMapper.toEntity(dto, cliente);
        assertNotNull(end);
        assertEquals("Log", end.getLogradouro());
        assertEquals(cliente, end.getCliente());
        assertNotNull(end.getCreatedAt());
    }

    @Test
    void toDto_mapsFields() {
        Cliente c = new Cliente();
        c.setId(2);
        c.setNome("Nome");
        c.setTelefone("119");
        EnderecoCliente e = new EnderecoCliente();
        e.setId(5);
        e.setLogradouro("L");
        e.setNumLogradouro("10");
        e.setBairro("B");
        e.setCep("C");
        e.setUf("UF");
        e.setCidade("City");
        e.setEstado("State");
        e.setMunicipio("Mun");
        e.setComplemento("Comp");
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());
        e.setAtivo(true);
        c.setEnderecoClientes(List.of(e));
        c.setCreatedAt(LocalDateTime.now());

        var dto = ClienteMapper.toDto(c);
        assertNotNull(dto);
        assertEquals(2, dto.id());
        assertEquals("Nome", dto.nome());
        assertEquals(1, dto.enderecos().size());
    }
}

