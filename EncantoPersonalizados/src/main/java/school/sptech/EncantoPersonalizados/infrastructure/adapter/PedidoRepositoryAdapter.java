package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.PedidoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PedidoRepositoryAdapter implements PedidoGateway {

    private final PedidoRepository repository;

    public PedidoRepositoryAdapter(PedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Pedido> findByIdWithDetails(Integer id) {
        return repository.findByIdWithDetails(id);
    }

    @Override
    public Page<Pedido> filtrar(String search, Boolean ativo, LocalDate inicio, LocalDate fim, Pageable pageable, Integer size) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        return repository.filtrar(search, ativo, inicioDateTime, fimDateTime, pageable);
    }
}
