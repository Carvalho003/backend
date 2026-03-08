package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.service.ContraparteService;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    private ContraparteService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void create_returns201_whenValid() throws Exception {
        RequestContraparteDTO req = new RequestContraparteDTO("Nome","Desc","Seg","Tipo");
        ResponseContraparteDTO resp = new ResponseContraparteDTO("Nome","Desc","Seg","Tipo");
        Mockito.when(service.create(any())).thenReturn(resp);

        mockMvc.perform(post("/contrapartes")
                        .with(csrf())
                        .with(user("test").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getById_returns204_whenNotFound() throws Exception {
        Mockito.when(service.getById(9)).thenReturn(null);
        mockMvc.perform(get("/contrapartes/9")).andExpect(status().isNoContent());
    }

    @Test
    void get_returns200() throws Exception {
        Mockito.when(service.get(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(resp())));
        mockMvc.perform(get("/contrapartes")).andExpect(status().isOk());
    }

    private ResponseContraparteDTO resp() {
        return new ResponseContraparteDTO("N","D","S","T");
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public ContraparteService contraparteService() {
            return Mockito.mock(ContraparteService.class);
        }
    }
}
