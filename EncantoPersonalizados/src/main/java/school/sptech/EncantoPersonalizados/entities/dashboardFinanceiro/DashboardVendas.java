package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "v_dash_vendas_categoria")
public class DashboardVendas {
    @Id
    private Long id;
    private String nomeCategoria;
    private BigDecimal valorTotalVendido;
    private Long quantidadePedidos;
    private LocalDate dataReferencia;

    public String getNomeCategoria() { return nomeCategoria; }
    public BigDecimal getValorTotalVendido() { return valorTotalVendido; }
    public Long getQuantidadePedidos() { return quantidadePedidos; }
}
