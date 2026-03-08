package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.dto.usuario.LoginRequestDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.service.LoginService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LoginController.class)
@Import(LoginControllerTest.Config.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_returns200_whenValid() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("a@b.c"); req.setPassword("senha");
        UserTokenDTO token = new UserTokenDTO(); token.setUserId(1); token.setEmail("a@b.c"); token.setNome("A"); token.setToken("t");
        Mockito.when(service.validateLogin(anyString(), anyString())).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void login_returns404_whenInvalid() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO(); req.setEmail("x@x"); req.setPassword("p");
        Mockito.when(service.validateLogin(anyString(), anyString())).thenReturn(null);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public LoginService loginService() {
            return Mockito.mock(LoginService.class);
        }
    }
}
