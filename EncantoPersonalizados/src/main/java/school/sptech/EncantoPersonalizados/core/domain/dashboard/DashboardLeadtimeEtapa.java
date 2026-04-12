package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_leadtime_etapa")
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
