package school.sptech.EncantoPersonalizados.controller;

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
import school.sptech.EncantoPersonalizados.dto.cliente.CreateClienteDTO;
import school.sptech.EncantoPersonalizados.dto.cliente.EnderecoClienteRequestDTO;
import school.sptech.EncantoPersonalizados.dto.cliente.ResponseClienteDTO;
import school.sptech.EncantoPersonalizados.dto.cliente.EnderecoClienteResponseDTO;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;
import school.sptech.EncantoPersonalizados.service.ClienteService;
import school.sptech.EncantoPersonalizados.service.EnderecoClienteService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
@Import(ClienteControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criar_returns201_whenValid() throws Exception {
        CreateClienteDTO req = new CreateClienteDTO("Nome","119", List.of());
        ResponseClienteDTO resp = new ResponseClienteDTO(1, "Nome", "119", List.of(), null, null);
        Mockito.when(clienteService.store(Mockito.any())).thenReturn(new Cliente());
        Mockito.when(clienteService.store(Mockito.any())).thenReturn(new Cliente());
        // ClienteMapper.toDto will be called inside controller; we mock service to return entity
        Mockito.when(clienteService.store(Mockito.any())).thenReturn(new Cliente());

        // Instead of mocking mapper, just mock service.criar to return entity and allow response 201
        Mockito.when(clienteService.store(Mockito.any())).thenReturn(new Cliente());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void criarEndereco_returns201_whenValid() throws Exception {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO("Log", "10", "B", "12345", "UF", "City", "State", "Mun", "Comp");
        EnderecoCliente e = new EnderecoCliente(); e.setId(5);
        Mockito.when(enderecoClienteService.store(Mockito.any(), Mockito.eq(1))).thenReturn(e);

        mockMvc.perform(post("/clientes/1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(clienteService.getAll(Mockito.any(), Mockito.anyInt())).thenReturn(new PageImpl<>(List.of(new Cliente())));
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ClienteService clienteService() {
            return Mockito.mock(ClienteService.class);
        }

        @org.springframework.context.annotation.Bean
        public EnderecoClienteService enderecoClienteService() {
            return Mockito.mock(EnderecoClienteService.class);
        }
    }
}
