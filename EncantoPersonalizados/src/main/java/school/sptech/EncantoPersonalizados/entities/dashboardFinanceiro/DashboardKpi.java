package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@jakarta.persistence.Table(name = "V_DASH_KPI_MES")
public class DashboardKpi {
    @Id
    private Long id;
    private String tipoMovimentacao;
    private Double valorMesAtual;
    private Double valorMesAnterior;
    private Double percentualVariacao;
    private LocalDate mesReferencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Double getValorMesAtual() {
        return valorMesAtual;
    }

    public void setValorMesAtual(Double valorMesAtual) {
        this.valorMesAtual = valorMesAtual;
    }

    public Double getValorMesAnterior() {
        return valorMesAnterior;
    }

    public void setValorMesAnterior(Double valorMesAnterior) {
        this.valorMesAnterior = valorMesAnterior;
    }

    public Double getPercentualVariacao() {
        return percentualVariacao;
    }

    public void setPercentualVariacao(Double percentualVariacao) {
        this.percentualVariacao = percentualVariacao;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
    }
}
