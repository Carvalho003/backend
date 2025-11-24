package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "v_dash_kpi_mes")
public class DashboardKpi {
    @Id
    private Long id;
    private String tipoMovimentacao;
    private BigDecimal valorMesAtual;
    private BigDecimal valorMesAnterior;
    private Double percentualVariacao;
    private LocalDate mesReferencia;

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public BigDecimal getValorMesAtual() { return valorMesAtual; }
    public BigDecimal getValorMesAnterior() { return valorMesAnterior; }
    public Double getPercentualVariacao() { return percentualVariacao; }
    public LocalDate getMesReferencia() { return mesReferencia; }
}
