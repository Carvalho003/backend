package school.sptech.EncantoPersonalizados.entities;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;
    private String descricao;
    private Double valor;
    @ManyToOne
    @JoinColumn(name = "contraparte_id")
    private Contraparte contraparte;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "categoria_movimentacao_id")
    private CategoriaMovimentacao categoriaMovimentacao;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (status == null) status = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Contraparte getContraparte() {
        return contraparte;
    }

    public void setContraparte(Contraparte contraparte) {
        this.contraparte = contraparte;
    }

    public CategoriaMovimentacao getCategoriaMovimentacao() { return categoriaMovimentacao; }

    public void setCategoriaMovimentacao(CategoriaMovimentacao categoriaMovimentacao) { this.categoriaMovimentacao = categoriaMovimentacao; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }
}
