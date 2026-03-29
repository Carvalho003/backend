package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;

import java.util.Optional;

public interface ContraparteRepository extends JpaRepository<Contraparte, Integer> {
    Optional<Contraparte> findByIdAndStatusTrue(Integer id);

    @Query("""
        SELECT c FROM Contraparte c
        WHERE (:search IS NULL OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:tipo IS NULL OR LOWER(c.tipoContrato) LIKE LOWER(CONCAT('%', :tipo, '%')))
        AND (:segmento IS NULL OR LOWER(c.segmento) LIKE LOWER(CONCAT('%', :segmento, '%')))
        AND (:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (c.status = COALESCE(:status, TRUE ))
    """)
    Page<Contraparte> filtrar(
            @Param("search") String search,
            @Param("tipo") String tipo,
            @Param("segmento") String segmento,
            @Param("nome") String nome,
            @Param("status") Boolean status,
            Pageable pageable
    );

}
