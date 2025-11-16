package school.sptech.EncantoPersonalizados.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;

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
}
