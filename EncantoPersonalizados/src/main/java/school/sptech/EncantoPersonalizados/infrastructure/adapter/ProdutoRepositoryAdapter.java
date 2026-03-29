package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ProdutoRepository;

import java.util.Optional;

@Repository
public class ProdutoRepositoryAdapter implements ProdutoGateway {

    private final ProdutoRepository repository;

    public ProdutoRepositoryAdapter(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<Produto> filtrar(String search, String categoria, String tema, String item, Boolean ativo, Pageable pageable) {
        return repository.filtrar(search, categoria, tema, item, ativo, pageable);
    }
}
