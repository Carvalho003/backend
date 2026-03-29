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
import school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido.StatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.core.application.usecase.statusPedido.StatusPedidoUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatusPedidoController.class)
@Import(StatusPedidoControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class StatusPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatusPedidoUseCase statusPedidoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public StatusPedidoUseCase statusPedidoUseCase() {
            return Mockito.mock(StatusPedidoUseCase.class);
        }
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(statusPedidoUseCase.listar(Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(new PageImpl<>(List.of(new StatusPedido())));
        mockMvc.perform(get("/status-pedidos")).andExpect(status().isOk());
    }

    @Test
    void criar_returns201() throws Exception {
        StatusPedidoRequestDto req = new StatusPedidoRequestDto("S", "#fff", 1);
        Mockito.when(statusPedidoUseCase.store(Mockito.any())).thenReturn(new StatusPedido());
        mockMvc.perform(post("/status-pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }
}
