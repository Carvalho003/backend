package school.sptech.EncantoPersonalizados.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.core.application.usecase.usuario.UsuarioUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@Import(UsuarioControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioUseCase usuarioUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public UsuarioUseCase usuarioUseCase() {
            return Mockito.mock(UsuarioUseCase.class);
        }
    }

    @Test
    void getById_returns200_whenFound() throws Exception {
        UsuarioResponseDTO dto = new UsuarioResponseDTO(1, "Nome", "email@test.com", null, "123", LocalDate.now(), "Cargo", LocalDateTime.now(), null);
        Mockito.when(usuarioUseCase.getById(1)).thenReturn(dto);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_returns404_whenNotFound() throws Exception {
        Mockito.when(usuarioUseCase.getById(2)).thenReturn(null);

        mockMvc.perform(get("/usuarios/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void store_returns201_whenValid() throws Exception {
        UsuarioRequestDTO req = new UsuarioRequestDTO("Nome", "email@test.com", "senha123", null, "12345678901", LocalDate.now(), "Cargo");
        UsuarioResponseDTO resp = new UsuarioResponseDTO(5, "Nome", "email@test.com", null, "12345678901", LocalDate.now(), "Cargo", LocalDateTime.now(), null);

        Mockito.when(usuarioUseCase.store(any())).thenReturn(resp);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void destroy_returns204_whenTrue_and404_whenFalse() throws Exception {
        Mockito.when(usuarioUseCase.destroy(10)).thenReturn(true);
        mockMvc.perform(delete("/usuarios/10")).andExpect(status().isNoContent());

        Mockito.when(usuarioUseCase.destroy(11)).thenReturn(false);
        mockMvc.perform(delete("/usuarios/11")).andExpect(status().isNotFound());
    }
}
