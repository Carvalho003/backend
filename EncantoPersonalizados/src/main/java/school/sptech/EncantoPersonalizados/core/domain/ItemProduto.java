package school.sptech.EncantoPersonalizados.core.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ItemProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private Double precoVenda;
    private Double custoProducao;
    private Integer prazoProducao;
    private Double largura;
    private Double altura;
    private Double peso;
    private Double comprimento;
    private String material;
    @Column(columnDefinition = "TEXT")
    private String descricaoPadrao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double precoPromocional;
    @OneToMany(mappedBy = "itemProduto")
    private List<Produto> produtos;
    private boolean ativo = true;


    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Double getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(Double precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getCustoProducao() {
        return custoProducao;
    }

    public void setCustoProducao(Double custoProducao) {
        this.custoProducao = custoProducao;
    }

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

    public Double getPrecoVenda() {
        return precoVenda;
    }



    public Integer getPrazoProducao() {
        return prazoProducao;
    }

    public void setPrazoProducao(Integer prazoProducao) {
        this.prazoProducao = prazoProducao;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescricaoPadrao() {
        return descricaoPadrao;
    }

    public void setDescricaoPadrao(String descricaoPadrao) {
        this.descricaoPadrao = descricaoPadrao;
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
