package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;

import java.util.List;
import java.util.Optional;

public interface ItemProdutoGateway {
    ItemProduto save(ItemProduto item);
    Optional<ItemProduto> findById(Integer id);
    Page<ItemProduto> filtrar(String search, boolean ativo, Pageable pageable);
    List<ItemProduto> findByPrecoVendaLessThan(Double preco);
    void deleteById(Integer id);
}
