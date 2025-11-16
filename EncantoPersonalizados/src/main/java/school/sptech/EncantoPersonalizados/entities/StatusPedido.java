package school.sptech.EncantoPersonalizados.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class StatusPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String status;
    @OneToMany(mappedBy = "status")
    private List<PedidoStatusPedido> pedidoStatusPedidos;
    private String cor;
    private Integer ordemKanban;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean ativo = true;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PedidoStatusPedido> getPedidoStatusPedidos() {
        return pedidoStatusPedidos;
    }

    public void setPedidoStatusPedidos(List<PedidoStatusPedido> pedidoStatusPedidos) {
        this.pedidoStatusPedidos = pedidoStatusPedidos;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getOrdemKanban() {
        return ordemKanban;
    }

    public void setOrdemKanban(Integer ordemKanban) {
        this.ordemKanban = ordemKanban;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
