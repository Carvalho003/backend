package school.sptech.EncantoPersonalizados.core.application.gateway;

import java.time.LocalDate;
import java.util.Map;

public interface DashboardGestaoPedidosGateway {
    Map<String, Object> getDashboardData(LocalDate inicio, LocalDate fim);
}
