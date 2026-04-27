package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT id, produto_id, qtd_prod FROM vw_filtro_produto_item")
@Synchronize({"pedido", "produto_pedido", "produto"})
public class DashboardFiltroProdutoItem {
    @Id
    private Long id;

    @Column(name = "produto_id")
    private Long produtoId;

    @Column(name = "qtd_prod")
    private Integer qtdProd;

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getQtdProd() {
        return qtdProd;
    }
}
