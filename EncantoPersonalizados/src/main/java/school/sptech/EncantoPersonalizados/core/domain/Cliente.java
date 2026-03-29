package school.sptech.EncantoPersonalizados.core.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String telefone;
    @OneToMany(mappedBy = "cliente")
    private List<EnderecoCliente> enderecoClientes;
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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

    public List<EnderecoCliente> getEnderecoClientes() {
        return enderecoClientes;
    }

    public void setEnderecoClientes(List<EnderecoCliente> enderecoClientes) {
        this.enderecoClientes = enderecoClientes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
