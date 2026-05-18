package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(cacheNames = "fotoProdutoById", key = "#foto.id", condition = "#foto.id != null")
    public FotoProduto save(FotoProduto foto) {
        return repository.save(foto);
    }

    @Override
    @Cacheable(cacheNames = "fotoProdutoById", key = "#id", unless = "#result == null")
    public Optional<FotoProduto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<FotoProduto> findByIdUncached(Integer id) {
        return repository.findById(id);
    }

    @Override
    @CacheEvict(cacheNames = "fotoProdutoById", key = "#foto.id", condition = "#foto.id != null")
    public void delete(FotoProduto foto) {
        if (foto.getId() != null) {
            repository.deleteById(foto.getId());
        }
    }
}
