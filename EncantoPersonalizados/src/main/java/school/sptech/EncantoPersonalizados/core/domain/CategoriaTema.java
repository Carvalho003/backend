package school.sptech.EncantoPersonalizados.core.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CategoriaTema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;

    private Boolean ativo = true;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @OneToMany(mappedBy = "categoriaTema")
    private List<TemaProduto> temaProdutos;

    public List<TemaProduto> getTemaProdutos() {
        return temaProdutos;
    }

    public void setTemaProdutos(List<TemaProduto> temaProdutos) {
        this.temaProdutos = temaProdutos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
