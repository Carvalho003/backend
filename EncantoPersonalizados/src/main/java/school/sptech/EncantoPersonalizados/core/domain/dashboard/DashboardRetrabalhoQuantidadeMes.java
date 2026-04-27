package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT mes, quantidade_pedidos FROM vw_retrabalho_quantidade_mes")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido"})
public class DashboardRetrabalhoQuantidadeMes {
    @Id
    private String mes;

    @Column(name = "quantidade_pedidos")
    private Long quantidadePedidos;

    public String getMes() {
        return mes;
    }

    public Long getQuantidadePedidos() {
        return quantidadePedidos;
    }
}
