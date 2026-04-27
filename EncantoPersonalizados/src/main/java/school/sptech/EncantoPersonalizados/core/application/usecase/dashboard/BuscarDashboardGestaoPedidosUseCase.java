package school.sptech.EncantoPersonalizados.core.application.usecase.dashboard;

import java.time.LocalDate;
import java.util.Map;

public interface BuscarDashboardGestaoPedidosUseCase {
    Map<String, Object> getDashboard(LocalDate inicio, LocalDate fim, String tipoPedido, Long produtoId, Long temaId);
}
