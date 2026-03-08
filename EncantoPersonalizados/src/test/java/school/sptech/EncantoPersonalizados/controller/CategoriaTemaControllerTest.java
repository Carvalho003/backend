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
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.service.CategoriaTemaService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoriaTemaController.class)
@Import(CategoriaTemaControllerTest.Config.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class CategoriaTemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaTemaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criar_returns201() throws Exception {
        // CategoriaTemaRequestDTO record has only 'titulo' field
        var req = new CategoriaTemaRequestDTO("Titulo");
        var resp = new CategoriaTemaResponseDTO(1, "Titulo", List.of());
        Mockito.when(service.criar(Mockito.any())).thenReturn(resp);

        mockMvc.perform(post("/categoria-temas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listar_returns200() throws Exception {
        // service.listar(search, ativo, page)
        Mockito.when(service.listar(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyInt())).thenReturn(new PageImpl<>(List.of(new CategoriaTema())));

        mockMvc.perform(get("/categoria-temas"))
                .andExpect(status().isOk());
    }

    @Test
    void mudarEstado_returns200() throws Exception {
        mockMvc.perform(patch("/categoria-temas/mudar-estado/5"))
                .andExpect(status().isOk());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public CategoriaTemaService categoriaTemaService() {
            return Mockito.mock(CategoriaTemaService.class);
        }
    }
}
