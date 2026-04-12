package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_leadtime_mensal")
public class DashboardLeadtimeMensal {
    @Id
    private String mes;

    @Column(name = "lead_time")
    private Double leadTime;

    public String getMes() {
        return mes;
    }

    public Double getLeadTime() {
        return leadTime;
    }
}
