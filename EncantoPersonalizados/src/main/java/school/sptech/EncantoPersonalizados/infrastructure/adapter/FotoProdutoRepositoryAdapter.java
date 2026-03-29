package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.FotoProdutoRepository;

import java.util.Optional;

@Repository
public class FotoProdutoRepositoryAdapter implements FotoProdutoGateway {

    private final FotoProdutoRepository repository;

    public FotoProdutoRepositoryAdapter(FotoProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public FotoProduto save(FotoProduto foto) {
        return repository.save(foto);
    }

    @Override
    public Optional<FotoProduto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void delete(FotoProduto foto) {
        repository.delete(foto);
    }
}
