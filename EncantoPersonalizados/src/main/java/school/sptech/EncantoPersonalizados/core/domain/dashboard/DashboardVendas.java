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
@Subselect("SELECT id, nome_categoria, valor_total_vendido, quantidade_pedidos, data_referencia FROM v_dash_vendas_categoria")
@Synchronize({"pedido", "produto_pedido", "produto", "tema_produto", "categoria_tema"})
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
