package school.sptech.EncantoPersonalizados.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.core.application.usecase.contraparte.ContraparteUseCase;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = ContraparteController.class)
@Import(ContraparteControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class ContraparteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContraparteUseCase contraparteUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_returns201_whenValid() throws Exception {
        RequestContraparteDTO req = new RequestContraparteDTO("Nome", "Desc", "Seg", "Tipo");
        ResponseContraparteDTO resp = new ResponseContraparteDTO(1, "Nome", "Desc", "Seg", "Tipo");
        Mockito.when(contraparteUseCase.create(any())).thenReturn(resp);

        mockMvc.perform(post("/contrapartes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void getById_returns204_whenNotFound() throws Exception {
        Mockito.when(contraparteUseCase.getById(9)).thenReturn(null);
        mockMvc.perform(get("/contrapartes/9")).andExpect(status().isNoContent());
    }

    @Test
    void get_returns200() throws Exception {
        Mockito.when(contraparteUseCase.get(any(), any(), any(), any(), any(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(resp())));
        mockMvc.perform(get("/contrapartes")).andExpect(status().isOk());
    }

    private ResponseContraparteDTO resp() {
        return new ResponseContraparteDTO(1, "N", "D", "S", "T");
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ContraparteUseCase contraparteUseCase() {
            return Mockito.mock(ContraparteUseCase.class);
        }
    }
}
