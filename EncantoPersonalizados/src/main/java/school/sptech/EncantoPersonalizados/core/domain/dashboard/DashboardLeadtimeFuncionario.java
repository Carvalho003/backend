package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_leadtime_funcionario")
public class DashboardLeadtimeFuncionario {
    @Id
    private String funcionario;

    @Column(name = "lead_time")
    private Double leadTime;

    public String getFuncionario() {
        return funcionario;
    }

    public Double getLeadTime() {
        return leadTime;
    }
}
