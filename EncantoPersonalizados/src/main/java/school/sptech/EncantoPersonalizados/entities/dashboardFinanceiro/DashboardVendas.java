package school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Mapeia para a View V_DASH_VENDAS_CATEGORIA
@Entity
@Table(name = "V_DASH_VENDAS_CATEGORIA")
public class DashboardVendas {

    @Id
    private Long id;
    private String nomeCategoria;
    private Double valorTotalVendido;
    private Long quantidadePedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Double getValorTotalVendido() {
        return valorTotalVendido;
    }

    public void setValorTotalVendido(Double valorTotalVendido) {
        this.valorTotalVendido = valorTotalVendido;
    }

    public Long getQuantidadePedidos() {
        return quantidadePedidos;
    }

    public void setQuantidadePedidos(Long quantidadePedidos) {
        this.quantidadePedidos = quantidadePedidos;
    }
}
