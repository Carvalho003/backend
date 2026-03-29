package school.sptech.EncantoPersonalizados.core.application.usecase.dashboard;

import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardFinanceiroGateway;

import java.time.LocalDate;
import java.util.Map;

@Component
public class BuscarDashboardFinanceiroUseCaseImpl implements BuscarDashboardFinanceiroUseCase {

    private final DashboardFinanceiroGateway gateway;

    public BuscarDashboardFinanceiroUseCaseImpl(DashboardFinanceiroGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Map<String, Object> getDashboard(LocalDate inicio, LocalDate fim) {
        return gateway.getDashboardData(inicio, fim);
    }
}
