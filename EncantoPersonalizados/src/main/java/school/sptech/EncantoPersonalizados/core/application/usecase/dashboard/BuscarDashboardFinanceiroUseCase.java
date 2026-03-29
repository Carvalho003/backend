package school.sptech.EncantoPersonalizados.core.application.usecase.dashboard;

import java.time.LocalDate;
import java.util.Map;

public interface BuscarDashboardFinanceiroUseCase {
    Map<String, Object> getDashboard(LocalDate inicio, LocalDate fim);
}
