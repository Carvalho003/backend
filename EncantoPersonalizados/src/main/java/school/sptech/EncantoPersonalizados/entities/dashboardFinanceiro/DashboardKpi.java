package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Column;
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

    @Column(name = "tipo_movimentacao")
    private String tipoMovimentacao;

    @Column(name = "valor_mes_atual")
    private BigDecimal valorMesAtual;

    @Column(name = "valor_mes_anterior")
    private BigDecimal valorMesAnterior;

    @Column(name = "percentual_variacao")
    private Double percentualVariacao;

    @Column(name = "mes_referencia")
    private LocalDate mesReferencia;

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public BigDecimal getValorMesAtual() { return valorMesAtual; }
    public BigDecimal getValorMesAnterior() { return valorMesAnterior; }
    public Double getPercentualVariacao() { return percentualVariacao; }
    public LocalDate getMesReferencia() { return mesReferencia; }
}
