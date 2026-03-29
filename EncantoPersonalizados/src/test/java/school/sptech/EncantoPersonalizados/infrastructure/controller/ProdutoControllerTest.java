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
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.application.usecase.produto.ProdutoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.ArmazenarFotoProdutoUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProdutoController.class)
@Import(ProdutoControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoUseCase produtoUseCase;

    @Autowired
    private ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criar_returns201() throws Exception {
        ProdutoRequestDTO req = new ProdutoRequestDTO("D", "T", 1, 1);
        ProdutoResponseDTO resp = new ProdutoResponseDTO(1, "T", "D", List.of(), null, null, null, null);
        Mockito.when(produtoUseCase.storeFull(any())).thenReturn(resp);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_returns200_whenFound() throws Exception {
        Mockito.when(produtoUseCase.findById(1)).thenReturn(new Produto());
        mockMvc.perform(get("/produtos/1")).andExpect(status().isOk());
    }

    @Test
    void get_returns200() throws Exception {
        Mockito.when(produtoUseCase.get(any(), any(), any(), any(), anyBoolean(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(new Produto())));
        mockMvc.perform(get("/produtos")).andExpect(status().isOk());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ProdutoUseCase produtoUseCase() {
            return Mockito.mock(ProdutoUseCase.class);
        }

        @org.springframework.context.annotation.Bean
        public ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase() {
            return Mockito.mock(ArmazenarFotoProdutoUseCase.class);
        }
    }
}
