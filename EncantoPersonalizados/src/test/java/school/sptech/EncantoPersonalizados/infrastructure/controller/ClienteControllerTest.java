package school.sptech.EncantoPersonalizados.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.CreateClienteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.EnderecoClienteRequestDTO;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.core.application.usecase.cliente.ClienteUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.enderecoCliente.EnderecoClienteUseCase;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
@Import(ClienteControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteUseCase clienteUseCase;

    @Autowired
    private EnderecoClienteUseCase enderecoClienteUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private static Cliente clienteWithEnderecos() {
        Cliente c = new Cliente();
        c.setEnderecoClientes(new ArrayList<>());
        return c;
    }

    @Test
    void criar_returns201_whenValid() throws Exception {
        CreateClienteDTO req = new CreateClienteDTO("Nome", "119", List.of());
        Mockito.when(clienteUseCase.store(Mockito.any())).thenReturn(clienteWithEnderecos());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void criarEndereco_returns201_whenValid() throws Exception {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO("Log", "10", "B", "12345", "UF", "City", "State", "Mun", "Comp");
        EnderecoCliente e = new EnderecoCliente();
        e.setId(5);
        Mockito.when(enderecoClienteUseCase.store(Mockito.any(), Mockito.eq(1))).thenReturn(e);

        mockMvc.perform(post("/clientes/1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(clienteUseCase.getAll(any(), anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(clienteWithEnderecos())));
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ClienteUseCase clienteUseCase() {
            return Mockito.mock(ClienteUseCase.class);
        }

        @org.springframework.context.annotation.Bean
        public EnderecoClienteUseCase enderecoClienteUseCase() {
            return Mockito.mock(EnderecoClienteUseCase.class);
        }
    }
}
