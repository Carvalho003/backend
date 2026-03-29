package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Produto;

import java.util.Optional;

public interface ProdutoGateway {
    Produto save(Produto produto);
    Optional<Produto> findById(Integer id);
    Page<Produto> filtrar(String search, String categoria, String tema, String item, Boolean ativo, Pageable pageable);
}
