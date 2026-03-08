package school.sptech.EncantoPersonalizados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.service.DashboardFinanceiroService;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DashboardFinanceiroController.class)
@Import(DashboardFinanceiroControllerTest.Config.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class DashboardFinanceiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DashboardFinanceiroService service;

    @Test
    void getDashboardFinanceiro_returns200() throws Exception {
        Mockito.when(service.getDashboardData(any(LocalDate.class), any(LocalDate.class))).thenReturn(Map.of("k", 1));

        mockMvc.perform(get("/dashfinanceiros?inicio=2026-01-01&fim=2026-02-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.k").value(1));
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public DashboardFinanceiroService dashboardFinanceiroService() {
            return Mockito.mock(DashboardFinanceiroService.class);
        }
    }
}
