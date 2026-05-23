package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PedidoGateway {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Integer id);
    Optional<Pedido> findByIdWithDetails(Integer id);
    Page<Pedido> filtrar(String search, Boolean ativo, LocalDate inicio, LocalDate fim, Pageable pageable, Integer size);
    Page<Pedido> filtrarAvancado(
            String search,
            Boolean ativo,
            String origem,
            Integer statusId,
            LocalDate createdAtInicio,
            LocalDate createdAtFim,
            LocalDate dataLimiteInicio,
            LocalDate dataLimiteFim,
            Double valorMin,
            Double valorMax,
            String sortBy,
            String sortDirection,
            Pageable pageable
    );
}
