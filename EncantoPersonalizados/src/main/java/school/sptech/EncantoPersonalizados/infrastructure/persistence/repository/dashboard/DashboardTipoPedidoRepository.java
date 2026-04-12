package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardTipoPedido;

import java.util.List;

public interface DashboardTipoPedidoRepository extends JpaRepository<DashboardTipoPedido, Long> {

    List<DashboardTipoPedido> findByTipoPedido(String tipoPedido);
}
