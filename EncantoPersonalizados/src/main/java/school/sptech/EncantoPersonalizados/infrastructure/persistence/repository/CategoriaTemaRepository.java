package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;

public interface CategoriaTemaRepository extends JpaRepository<CategoriaTema, Integer> {

    @Query("""
            SELECT ct FROM CategoriaTema ct
            WHERE (:search IS NULL OR LOWER(ct.titulo) LIKE LOWER(CONCAT('%', :search, '%')) )
            AND (ct.ativo = :ativo)
            """)
    Page<CategoriaTema> filtrar(
            @Param("search") String search,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );
}
