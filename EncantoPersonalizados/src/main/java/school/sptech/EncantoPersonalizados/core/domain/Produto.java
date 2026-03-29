package school.sptech.EncantoPersonalizados.core.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private String titulo;
    @ManyToOne
    private TemaProduto temaProduto;
    @ManyToOne
    private ItemProduto itemProduto;
    @OneToMany(mappedBy = "produto")
    private List<FotoProduto> fotoProdutos;
    private Boolean ativo = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TemaProduto getTemaProduto() {
        return temaProduto;
    }

    public void setTemaProduto(TemaProduto temaProduto) {
        this.temaProduto = temaProduto;
    }

    public ItemProduto getItemProduto() {
        return itemProduto;
    }

    public void setItemProduto(ItemProduto itemProduto) {
        this.itemProduto = itemProduto;
    }

    public List<FotoProduto> getFotoProdutos() {
        return fotoProdutos;
    }

    public void setFotoProdutos(List<FotoProduto> fotoProdutos) {
        this.fotoProdutos = fotoProdutos;
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

    public Boolean getAtivo() { return ativo; }

    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

}
