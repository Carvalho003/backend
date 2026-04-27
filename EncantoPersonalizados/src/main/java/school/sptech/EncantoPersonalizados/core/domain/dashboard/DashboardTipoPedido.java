package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT id, origem, observacoes, status, tipo_pedido FROM vw_tipo_pedido")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido"})
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
