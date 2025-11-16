package school.sptech.EncantoPersonalizados.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String observacoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "pedido")
    private List<PedidoStatusPedido> pedidoStatusPedidos;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido")
    private List<ProdutoPedido> produtoPedidos;

    private boolean ativo = true;


    public List<PedidoStatusPedido> getPedidoStatusPedidos() {
        return pedidoStatusPedidos;
    }

    public void setPedidoStatusPedidos(List<PedidoStatusPedido> pedidoStatusPedidos) {
        this.pedidoStatusPedidos = pedidoStatusPedidos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ProdutoPedido> getProdutoPedidos() {
        return produtoPedidos;
    }

    public void setProdutoPedidos(List<ProdutoPedido> produtoPedidos) {
        this.produtoPedidos = produtoPedidos;
    }
}
