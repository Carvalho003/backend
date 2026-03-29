package school.sptech.EncantoPersonalizados.core.domain;

public class FotoArquivo {

    private final String caminhoRelativo;
    private final String nomeArquivo;
    private final Integer produtoId;

    public FotoArquivo(String caminhoRelativo, String nomeArquivo, Integer produtoId) {
        this.caminhoRelativo = caminhoRelativo;
        this.nomeArquivo = nomeArquivo;
        this.produtoId = produtoId;
    }

    public String getCaminhoRelativo() {
        return caminhoRelativo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public Integer getProdutoId() {
        return produtoId;
    }
}
