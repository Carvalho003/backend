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
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.application.usecase.itemProduto.ItemProdutoUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemProdutoController.class)
@Import(ItemProdutoControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemProdutoUseCase itemProdutoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrar_returns201() throws Exception {
        ItemProdutoRequestDTO req = new ItemProdutoRequestDTO("desc", 10.0, 5.0, 2, 10.0, 5.0, 200.0, 15.0, "mat", "a", 8.0);
        Mockito.when(itemProdutoUseCase.cadastrar(any())).thenReturn(null);
        mockMvc.perform(post("/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(itemProdutoUseCase.listar(any(), anyBoolean(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(new ItemProduto())));
        mockMvc.perform(get("/itens")).andExpect(status().isOk());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ItemProdutoUseCase itemProdutoUseCase() {
            return Mockito.mock(ItemProdutoUseCase.class);
        }
    }
}
