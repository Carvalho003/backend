package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardGestaoPedidosGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.impl.BuscarDashboardGestaoPedidosUseCaseImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarDashboardGestaoPedidosUseCaseImplTest {

    @Mock
    private DashboardGestaoPedidosGateway gateway;

    @InjectMocks
    private BuscarDashboardGestaoPedidosUseCaseImpl useCase;

    private final LocalDate inicio = LocalDate.of(2025, 1, 1);
    private final LocalDate fim = LocalDate.of(2025, 3, 31);

    @Test
    @DisplayName("getDashboard - delega ao gateway com todos os parâmetros incluindo datas")
    void getDashboard_delegatesToGatewayWithAllParams() {
        Map<String, Object> expected = Map.of("tiposPedido", List.of());
        when(gateway.getDashboardData(inicio, fim, "Normal", 1L, 2L)).thenReturn(expected);

        Map<String, Object> result = useCase.getDashboard(inicio, fim, "Normal", 1L, 2L);

        assertEquals(expected, result);
        verify(gateway, times(1)).getDashboardData(inicio, fim, "Normal", 1L, 2L);
    }

    @Test
    @DisplayName("getDashboard - com opcionais nulos ainda delega ao gateway")
    void getDashboard_withNullOptionals_stillDelegates() {
        when(gateway.getDashboardData(inicio, fim, null, null, null)).thenReturn(Map.of());

        useCase.getDashboard(inicio, fim, null, null, null);

        verify(gateway, times(1)).getDashboardData(inicio, fim, null, null, null);
    }

    @Test
    @DisplayName("getDashboard - retorna mapa vazio quando gateway retorna vazio")
    void getDashboard_returnsEmptyMap_whenGatewayReturnsEmpty() {
        when(gateway.getDashboardData(inicio, fim, null, null, null)).thenReturn(Map.of());

        Map<String, Object> result = useCase.getDashboard(inicio, fim, null, null, null);

        assertTrue(result.isEmpty());
    }
}
