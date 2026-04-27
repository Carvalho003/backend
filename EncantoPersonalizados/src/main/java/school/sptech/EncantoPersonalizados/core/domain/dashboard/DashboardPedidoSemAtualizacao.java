package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT id, cliente, status, dias_parado, responsavel FROM vw_pedidos_sem_atualizacao")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido", "cliente", "usuario"})
public class DashboardPedidoSemAtualizacao {

    @Id
    private Long id;

    private String cliente;

    private String status;

    @Column(name = "dias_parado")
    private Long diasParado;

    private String responsavel;

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public String getStatus() { return status; }
    public Long getDiasParado() { return diasParado; }
    public String getResponsavel() { return responsavel; }
}
