package school.sptech.EncantoPersonalizados.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CategoriaMovimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private Boolean status;
    @OneToMany(mappedBy = "categoriaMovimentacao")
    List<Movimentacao> movimentacaos;

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Movimentacao> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<Movimentacao> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }
}
