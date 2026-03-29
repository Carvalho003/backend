package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query("""
        SELECT DISTINCT p FROM Produto p
        LEFT JOIN p.temaProduto t
        LEFT JOIN t.categoriaTema c
        LEFT JOIN p.itemProduto i
        WHERE (:search IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:categoria IS NULL OR c.titulo = :categoria)
        AND (:tema IS NULL OR t.descricao = :tema)
        AND (:item IS NULL OR i.descricao = :item)
        AND (p.ativo = :ativo)
    """)
    Page<Produto> filtrar(
            @Param("search") String search,
            @Param("categoria") String categoria,
            @Param("tema") String tema,
            @Param("item") String item,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );
}
