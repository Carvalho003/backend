package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
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

    @Column(name = "nome_categoria")
    private String nomeCategoria;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_referencia")
    private LocalDate dataReferencia;

    public String getNomeCategoria() { return nomeCategoria; }
    public BigDecimal getValorTotal() { return valorTotal; }
}
