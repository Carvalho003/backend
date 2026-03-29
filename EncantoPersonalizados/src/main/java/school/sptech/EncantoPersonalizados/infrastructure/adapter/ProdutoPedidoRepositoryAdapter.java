package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ProdutoPedidoRepository;

import java.util.Optional;

@Repository
public class ProdutoPedidoRepositoryAdapter implements ProdutoPedidoGateway {

    private final ProdutoPedidoRepository repository;

    public ProdutoPedidoRepositoryAdapter(ProdutoPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProdutoPedido save(ProdutoPedido entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<ProdutoPedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
