package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.facade.ProdutoFacade;
import school.sptech.EncantoPersonalizados.service.ProdutoService;
import school.sptech.EncantoPersonalizados.service.FotoProdutoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoFacade facade;

    @MockitoBean
    private ProdutoService service;

    @MockitoBean
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criar_returns201() throws Exception {
        ProdutoRequestDTO req = new ProdutoRequestDTO("D","T",1,1);
        ProdutoResponseDTO resp = new ProdutoResponseDTO(1,"T","D", List.of(), null, null, null, null);
        Mockito.when(facade.store(any())).thenReturn(resp);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_returns200_whenFound() throws Exception {
        Mockito.when(service.findById(1)).thenReturn(new Produto());
        mockMvc.perform(get("/produtos/1")).andExpect(status().isOk());
    }

    @Test
    void get_returns200() throws Exception {
        Mockito.when(service.get(any(), any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(List.of(new Produto())));
        mockMvc.perform(get("/produtos")).andExpect(status().isOk());
    }
}

