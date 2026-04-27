package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT funcionario, lead_time, total_pedidos FROM vw_leadtime_funcionario")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido", "usuario"})
public class DashboardLeadtimeFuncionario {
    @Id
    private String funcionario;

    @Column(name = "lead_time")
    private Double leadTime;

    @Column(name = "total_pedidos")
    private Long totalPedidos;

    public String getFuncionario() {
        return funcionario;
    }

    public Double getLeadTime() {
        return leadTime;
    }

    public Long getTotalPedidos() {
        return totalPedidos;
    }
}
