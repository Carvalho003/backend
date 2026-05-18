package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("""
            SELECT c FROM Cliente c
            WHERE (:search IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :search, '%')) )
            AND c.ativo = true
            """)
    Page<Cliente> filtrar(
            @Param("search") String search,
            Pageable pageable
    );
}
