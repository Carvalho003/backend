package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.ItemProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ItemProdutoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemProdutoRepositoryAdapter implements ItemProdutoGateway {

    private final ItemProdutoRepository repository;

    public ItemProdutoRepositoryAdapter(ItemProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemProduto save(ItemProduto item) {
        return repository.save(item);
    }

    @Override
    public Optional<ItemProduto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<ItemProduto> filtrar(String search, boolean ativo, Pageable pageable) {
        return repository.filtrar(search, ativo, pageable);
    }

    @Override
    public List<ItemProduto> findByPrecoVendaLessThan(Double preco) {
        return repository.findByPrecoVendaLessThan(preco).orElse(List.of());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
