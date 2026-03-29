package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoStatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.PedidoStatusPedidoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoStatusPedidoRepositoryAdapter implements PedidoStatusPedidoGateway {

    private final PedidoStatusPedidoRepository repository;

    public PedidoStatusPedidoRepositoryAdapter(PedidoStatusPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PedidoStatusPedido salvar(PedidoStatusPedido entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<PedidoStatusPedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PedidoStatusPedido> findStatusAtualByPedidoId(Integer pedidoId) {
        return repository.findStatusAtualByPedidoId(pedidoId);
    }
}
