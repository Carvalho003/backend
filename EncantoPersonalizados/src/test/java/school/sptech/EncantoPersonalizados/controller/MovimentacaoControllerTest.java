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
import school.sptech.EncantoPersonalizados.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.service.MovimentacaoService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovimentacaoController.class)
class MovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimentacaoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_returns201() throws Exception {
        RequestMovimentacaoDTO req = new RequestMovimentacaoDTO("ENTRADA","Desc",100.0,"PAGO", LocalDate.now(), null, 1, 2);
        ResponseMovimentacaoDTO resp = new ResponseMovimentacaoDTO("ENTRADA","Desc",100.0,"PAGO", LocalDate.now(), null, null, null);
        Mockito.when(service.create(any())).thenReturn(resp);

        mockMvc.perform(post("/movimentacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("ENTRADA"));
    }

    @Test
    void getById_returns404_whenNotFound() throws Exception {
        Mockito.when(service.getById(5)).thenReturn(null);
        mockMvc.perform(get("/movimentacoes/5")).andExpect(status().isNotFound());
    }

    @Test
    void get_returns204_whenEmpty() throws Exception {
        Mockito.when(service.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(get("/movimentacoes")).andExpect(status().isNoContent());
    }
}

