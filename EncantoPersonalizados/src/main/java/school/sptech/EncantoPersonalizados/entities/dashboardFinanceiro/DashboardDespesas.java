package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "v_dash_despesas_categoria")
public class DashboardDespesas {
    @Id
    private Long id;
    private String nomeCategoria;
    private BigDecimal valorTotal;
    private LocalDate dataReferencia;

    public String getNomeCategoria() { return nomeCategoria; }
    public BigDecimal getValorTotal() { return valorTotal; }
}
