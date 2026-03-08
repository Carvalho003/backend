package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.service.CategoriaMovimentacaoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoriaMovimentacaoController.class)
@Import(CategoriaMovimentacaoControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriaMovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaMovimentacaoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_returns201() throws Exception {
        RequestCategoriaMovimentacaoDTO req = new RequestCategoriaMovimentacaoDTO("Desc");
        ResponseCategoriaMovimentacaoDTO resp = new ResponseCategoriaMovimentacaoDTO(1, "Desc");
        Mockito.when(service.create(any())).thenReturn(resp);

        mockMvc.perform(post("/categoria-movimentacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_returns200_whenFound() throws Exception {
        ResponseCategoriaMovimentacaoDTO resp = new ResponseCategoriaMovimentacaoDTO(2, "X");
        Mockito.when(service.findById(2)).thenReturn(resp);

        mockMvc.perform(get("/categoria-movimentacoes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("X"));
    }

    @Test
    void get_returns204_whenEmpty() throws Exception {
        Mockito.when(service.get(any(), any(), any())).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(get("/categoria-movimentacoes"))
                .andExpect(status().isNoContent());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public CategoriaMovimentacaoService categoriaMovimentacaoService() {
            return Mockito.mock(CategoriaMovimentacaoService.class);
        }
    }
}
