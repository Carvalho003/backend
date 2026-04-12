package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_tipo_pedido")
public class DashboardTipoPedido {
    @Id
    private Long id;

    private String origem;

    private String observacoes;

    @Column(name = "status")
    private String status;

    @Column(name = "tipo_pedido")
    private String tipoPedido;

    public Long getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getStatus() {
        return status;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
}
