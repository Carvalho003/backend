package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Immutable
@Subselect("SELECT id, nome_categoria, valor_total, data_referencia FROM v_dash_despesas_categoria")
@Synchronize({"movimentacao", "categoria_movimentacao"})
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
