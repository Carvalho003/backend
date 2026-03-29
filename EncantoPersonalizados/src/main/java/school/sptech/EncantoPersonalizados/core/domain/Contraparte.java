package school.sptech.EncantoPersonalizados.core.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Contraparte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String segmento;
    private String tipoContrato;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "contraparte")
    private List<Movimentacao> movimentacao;
    private Boolean status;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (status == null) status = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
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

    public List<Movimentacao> getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(List<Movimentacao> movimentacao) {
        this.movimentacao = movimentacao;
    }
}
