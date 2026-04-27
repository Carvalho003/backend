package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT mes, total_criados, total_entregues FROM vw_pedidos_mes")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido"})
public class DashboardPedidosMes {

    @Id
    private String mes;

    @Column(name = "total_criados")
    private Long totalCriados;

    @Column(name = "total_entregues")
    private Long totalEntregues;

    public String getMes() { return mes; }
    public Long getTotalCriados() { return totalCriados; }
    public Long getTotalEntregues() { return totalEntregues; }
}
