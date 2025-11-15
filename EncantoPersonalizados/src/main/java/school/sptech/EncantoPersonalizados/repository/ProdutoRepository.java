package school.sptech.EncantoPersonalizados.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query("""
        SELECT p FROM Produto p
        WHERE (:search IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :search, '%')))  
        AND (:categoria IS NULL OR p.temaProduto.categoriaTema.titulo = :categoria)
        AND (:tema IS NULL OR p.temaProduto.descricao = :tema)
        AND (:item IS NULL OR p.itemProduto.descricao = :item)
        AMD (p.ativo = :ativo)
    """)
    Page<Produto> filtrar(
            @Param("search") String search,
            @Param("categoria") String categoria,
            @Param("tema") String tema,
            @Param("item") String item,
            @Param("ativo") Integer ativo,
            Pageable pageable
    );
}
