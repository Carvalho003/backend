package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

import java.util.Optional;

public interface TemaProdutoGateway {
    TemaProduto save(TemaProduto tema);
    Optional<TemaProduto> findById(Integer id);
    Page<TemaProduto> filtrar(String search, String categoria, boolean ativo, Pageable pageable);
}
