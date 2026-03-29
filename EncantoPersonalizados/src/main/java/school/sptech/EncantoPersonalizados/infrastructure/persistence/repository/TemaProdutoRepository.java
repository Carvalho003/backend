package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

public interface TemaProdutoRepository extends JpaRepository<TemaProduto, Integer> {
    @Query(
            """
            SELECT tp FROM TemaProduto tp
            WHERE (:search IS NULL OR LOWER(tp.descricao) LIKE LOWER(CONCAT('%', :search, '%')) )  
            AND (:categoria IS NULL OR :categoria = tp.categoriaTema.titulo)
            AND tp.ativo = :ativo      
            """
    )
    Page<TemaProduto> filtrar(
            @Param("search") String search,
            @Param("categoria") String categoria,
            @Param("ativo") boolean ativo,
            Pageable pageable
    );
}
