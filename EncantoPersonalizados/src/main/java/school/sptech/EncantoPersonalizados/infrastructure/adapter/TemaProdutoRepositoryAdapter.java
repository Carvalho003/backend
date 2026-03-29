package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.TemaProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.TemaProdutoRepository;

import java.util.Optional;

@Repository
public class TemaProdutoRepositoryAdapter implements TemaProdutoGateway {

    private final TemaProdutoRepository repository;

    public TemaProdutoRepositoryAdapter(TemaProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public TemaProduto save(TemaProduto tema) {
        return repository.save(tema);
    }

    @Override
    public Optional<TemaProduto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<TemaProduto> filtrar(String search, String categoria, boolean ativo, Pageable pageable) {
        return repository.filtrar(search, categoria, ativo, pageable);
    }
}
