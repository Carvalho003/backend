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
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.entities.ItemProduto;
import school.sptech.EncantoPersonalizados.service.ItemProdutoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemProdutoController.class)
class ItemProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemProdutoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrar_returns201() throws Exception {
        ItemProdutoRequestDTO req = new ItemProdutoRequestDTO("desc", 10.0, 5.0, 2, 10.0, 5.0, 200.0, 15.0, "mat", 8.0);
        Mockito.when(service.cadastrar(any())).thenReturn(null);
        mockMvc.perform(post("/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void listar_returns200() throws Exception {
        Mockito.when(service.listar(any(), any(), any())).thenReturn(new PageImpl<>(List.of(new ItemProduto())));
        mockMvc.perform(get("/itens")).andExpect(status().isOk());
    }
}

