package school.sptech.EncantoPersonalizados.core.domain.dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("SELECT funcionario, em_andamento FROM vw_carga_trabalho")
@Synchronize({"pedido", "pedido_status_pedido", "status_pedido", "usuario"})
public class DashboardCargaTrabalho {

    @Id
    private String funcionario;

    @Column(name = "em_andamento")
    private Long emAndamento;

    public String getFuncionario() { return funcionario; }
    public Long getEmAndamento() { return emAndamento; }
}
