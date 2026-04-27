package school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.impl;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardGestaoPedidosGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.BuscarDashboardGestaoPedidosUseCase;

import java.time.LocalDate;
import java.util.Map;

@Service
public class BuscarDashboardGestaoPedidosUseCaseImpl implements BuscarDashboardGestaoPedidosUseCase {

    private final DashboardGestaoPedidosGateway gateway;

    public BuscarDashboardGestaoPedidosUseCaseImpl(DashboardGestaoPedidosGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Map<String, Object> getDashboard(LocalDate inicio, LocalDate fim, String tipoPedido, Long produtoId, Long temaId) {
        return gateway.getDashboardData(inicio, fim, tipoPedido, produtoId, temaId);
    }
}
