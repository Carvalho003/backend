package school.sptech.EncantoPersonalizados.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CategoriaMovimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @OneToMany(mappedBy = "categoriaMovimentacao")
    List<Movimentacao> movimentacaos;

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
