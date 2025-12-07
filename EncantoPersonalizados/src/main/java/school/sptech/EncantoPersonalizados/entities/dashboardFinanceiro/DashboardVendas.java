package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Column;
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

    @Column(name = "nome_categoria")
    private String nomeCategoria;

    @Column(name = "valor_total_vendido")
    private BigDecimal valorTotalVendido;

    @Column(name = "quantidade_pedidos")
    private Long quantidadePedidos;

    @Column(name = "data_referencia")
    private LocalDate dataReferencia;

    public String getNomeCategoria() { return nomeCategoria; }
    public BigDecimal getValorTotalVendido() { return valorTotalVendido; }
    public Long getQuantidadePedidos() { return quantidadePedidos; }
}
