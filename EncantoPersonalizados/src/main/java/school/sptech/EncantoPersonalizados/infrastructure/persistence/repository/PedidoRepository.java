package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

    @Query("""
            SELECT p FROM Pedido p
            LEFT JOIN p.cliente c
            WHERE p.ativo = :ativo
            AND (:inicio IS NULL OR p.createdAt >= :inicio)
            AND (:fim IS NULL OR p.createdAt <= :fim)
            AND (:search IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.origem) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Pedido> filtrar(
            @Param("search") String search,
            @Param("ativo") Boolean ativo,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable

    );

    @Query("""
            SELECT p FROM Pedido p
            WHERE p.id = :pedidoId
            """)
    Optional<Pedido> findByIdWithDetails(@Param("pedidoId") Integer pedidoId);

}
