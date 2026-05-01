package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.StatusPedidoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class StatusPedidoRepositoryAdapter implements StatusPedidoGateway {

    private final StatusPedidoRepository repository;

    public StatusPedidoRepositoryAdapter(StatusPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public StatusPedido save(StatusPedido statusPedido) {
        return repository.save(statusPedido);
    }

    @Override
    public Optional<StatusPedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<StatusPedido> filtrar(Boolean ativo, Pageable pageable) {
        return repository.filtrar(ativo, pageable);
    }

    @Override
    public Optional<StatusPedido> findFirstByOrderByOrdemKanbanAsc() {
        return repository.findFirstByOrderByOrdemKanbanAsc();
    }

    @Override
    public List<StatusPedido> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByStatusIgnoreCaseAndAtivoTrue(String status) {
        return repository.existsByStatusIgnoreCaseAndAtivoTrue(status);
    }

    @Override
    public boolean existePedidoVinculado(Integer idStatus) {
        return repository.existePedidoVinculado(idStatus);
    }
}
