package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT etapa, lead_time FROM vw_leadtime_etapa")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido"})
public class DashboardLeadtimeEtapa {
    @Id
    private String etapa;

    @Column(name = "lead_time")
    private Double leadTime;

    public String getEtapa() {
        return etapa;
    }

    public Double getLeadTime() {
        return leadTime;
    }
}
