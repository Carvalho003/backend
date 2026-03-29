package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaMovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.CategoriaMovimentacaoRepository;

import java.util.Optional;

@Repository
public class CategoriaMovimentacaoRepositoryAdapter implements CategoriaMovimentacaoGateway {

    private final CategoriaMovimentacaoRepository repository;

    public CategoriaMovimentacaoRepositoryAdapter(CategoriaMovimentacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaMovimentacao save(CategoriaMovimentacao categoria) {
        return repository.save(categoria);
    }

    @Override
    public Optional<CategoriaMovimentacao> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<CategoriaMovimentacao> findByIdAndStatusTrue(Integer id) {
        return repository.findByIdAndStatusTrue(id);
    }

    @Override
    public Page<CategoriaMovimentacao> filtrar(String search, Boolean status, Pageable pageable) {
        return repository.filtrar(search, status, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByDescricao(String descricao) {
        return repository.existsByDescricao(descricao);
    }
}
