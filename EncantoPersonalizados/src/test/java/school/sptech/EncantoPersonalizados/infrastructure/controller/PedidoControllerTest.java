package school.sptech.EncantoPersonalizados.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.core.application.usecase.pedido.PedidoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.produtoPedido.ProdutoPedidoUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PedidoController.class)
@Import(PedidoControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoUseCase pedidoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public PedidoUseCase pedidoUseCase() {
            return Mockito.mock(PedidoUseCase.class);
        }

        @org.springframework.context.annotation.Bean
        public ProdutoPedidoUseCase produtoPedidoUseCase() {
            return Mockito.mock(ProdutoPedidoUseCase.class);
        }
    }

    @Test
    void criar_returns201() throws Exception {
        PedidoRequestDto req = new PedidoRequestDto("Obs", "Web", 1, 2, List.of());
        PedidoCreatedResponseDto resp = new PedidoCreatedResponseDto(1, "Obs", "Web", null);
        Mockito.when(pedidoUseCase.store(any())).thenReturn(resp);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(pedidoUseCase.listar(any(), any(), any())).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(get("/pedidos")).andExpect(status().isOk());
    }

    @Test
    void getById_returns200() throws Exception {
        Mockito.when(pedidoUseCase.getById(1)).thenReturn(
                new PedidoResponseDto(1, "Obs", "Web", null, 0.0, 0.0, null, null, List.of(), true, null, null, null, List.of()));
        mockMvc.perform(get("/pedidos/1")).andExpect(status().isOk());
    }
}
