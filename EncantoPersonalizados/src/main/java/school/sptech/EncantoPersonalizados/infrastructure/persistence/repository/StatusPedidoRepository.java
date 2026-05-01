package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;

import java.util.Optional;

public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer> {

    @Query("""
            SELECT sp
            FROM StatusPedido sp
            WHERE (sp.ativo = :ativo)
            """)
    Page<StatusPedido> filtrar(
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );

    Optional<StatusPedido> findFirstByOrderByOrdemKanbanAsc();

    boolean existsByStatusIgnoreCaseAndAtivoTrue(String status);

    @Query("SELECT CASE WHEN COUNT(psp) > 0 THEN true ELSE false END FROM PedidoStatusPedido psp WHERE psp.status.id = :idStatus")
    boolean existePedidoVinculado(@Param("idStatus") Integer idStatus);
}