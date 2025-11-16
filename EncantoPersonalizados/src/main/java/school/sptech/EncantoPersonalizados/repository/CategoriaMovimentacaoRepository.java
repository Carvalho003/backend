package school.sptech.EncantoPersonalizados.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;


import java.util.Optional;

public interface CategoriaMovimentacaoRepository extends JpaRepository<CategoriaMovimentacao,Integer>{
    boolean existsByDescricao(String descricao);

    Optional<CategoriaMovimentacao> findByIdAndStatusTrue(Integer id);

    @Query("""
        SELECT cm FROM CategoriaMovimentacao cm
        WHERE (:search IS NULL OR LOWER(cm.descricao) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (cm.status = COALESCE(:status, TRUE))
    """)
    Page<CategoriaMovimentacao> filtrar(
            @Param("search") String search,
            @Param("status") Boolean status,
            Pageable pageable
    );
}
