package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaTemaGateway;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.CategoriaTemaRepository;

import java.util.Optional;

@Repository
public class CategoriaTemaRepositoryAdapter implements CategoriaTemaGateway {

    private final CategoriaTemaRepository repository;

    public CategoriaTemaRepositoryAdapter(CategoriaTemaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaTema save(CategoriaTema categoria) {
        return repository.save(categoria);
    }

    @Override
    public Optional<CategoriaTema> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<CategoriaTema> filtrar(String search, Boolean ativo, Pageable pageable) {
        return repository.filtrar(search, ativo, pageable);
    }
}
