package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "vw_retrabalho_quantidade_mes")
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
