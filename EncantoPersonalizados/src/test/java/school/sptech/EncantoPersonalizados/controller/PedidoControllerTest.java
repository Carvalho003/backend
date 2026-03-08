package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.facade.PedidoFacade;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PedidoController.class)
@Import(PedidoControllerTest.Config.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoFacade facade;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public PedidoFacade pedidoFacade() {
            return Mockito.mock(PedidoFacade.class);
        }

        // ProdutoPedidoService não é usado neste teste; não registrar mock
    }

    @Test
    void criar_returns201() throws Exception {
        PedidoRequestDto req = new PedidoRequestDto("Obs","Web",1,2, List.of());
        // PedidoCreatedResponseDto requires (id, observacoes, origem, dataLimite)
        PedidoCreatedResponseDto resp = new PedidoCreatedResponseDto(1, "Obs", "Web", null);
        Mockito.when(facade.store(any())).thenReturn(resp);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(facade.listar(any(), any(), any())).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(get("/pedidos")).andExpect(status().isOk());
    }

    @Test
    void getById_returns200() throws Exception {
        Mockito.when(facade.getById(1)).thenReturn(new PedidoResponseDto(1, "Obs","Web", null, 0.0, 0.0, null, null, List.of(), true, null, null, null, List.of()));
        mockMvc.perform(get("/pedidos/1")).andExpect(status().isOk());
    }
}
