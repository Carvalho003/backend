package school.sptech.EncantoPersonalizados.infrastructure.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.BuscarDashboardGestaoPedidosUseCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DashboardGestaoPedidosController.class)
@Import(DashboardGestaoPedidosControllerTest.Config.class)
@AutoConfigureMockMvc(addFilters = false)
class DashboardGestaoPedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuscarDashboardGestaoPedidosUseCase buscarDashboardGestaoPedidosUseCase;

    @Test
    @DisplayName("GET /dashgestaopedidos - datas válidas retorna 200")
    void getDashboard_withValidDates_returns200() throws Exception {
        mockMvc.perform(get("/dashgestaopedidos?inicio=2025-01-01&fim=2025-03-31"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /dashgestaopedidos - todos os filtros retorna 200")
    void getDashboard_withAllFilters_returns200() throws Exception {
        mockMvc.perform(get("/dashgestaopedidos?inicio=2025-01-01&fim=2025-03-31&tipoPedido=Normal&produtoId=1&temaId=2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /dashgestaopedidos - sem inicio retorna 400")
    void getDashboard_missingInicio_returns400() throws Exception {
        mockMvc.perform(get("/dashgestaopedidos?fim=2025-03-31"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /dashgestaopedidos - sem fim retorna 400")
    void getDashboard_missingFim_returns400() throws Exception {
        mockMvc.perform(get("/dashgestaopedidos?inicio=2025-01-01"))
                .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    static class Config {
        @Bean
        public BuscarDashboardGestaoPedidosUseCase buscarDashboardGestaoPedidosUseCase() {
            BuscarDashboardGestaoPedidosUseCase mock = Mockito.mock(BuscarDashboardGestaoPedidosUseCase.class);
            Mockito.when(mock.getDashboard(
                    any(LocalDate.class), any(LocalDate.class),
                    any(), any(), any()
            )).thenReturn(Map.of("tiposPedido", List.of()));
            return mock;
        }
    }
}
