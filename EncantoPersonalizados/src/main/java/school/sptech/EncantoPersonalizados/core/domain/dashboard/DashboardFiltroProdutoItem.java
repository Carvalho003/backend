package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_filtro_produto_item")
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
