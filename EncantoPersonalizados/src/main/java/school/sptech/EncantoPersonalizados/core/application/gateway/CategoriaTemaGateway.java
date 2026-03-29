package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;

import java.util.Optional;

public interface CategoriaTemaGateway {
    CategoriaTema save(CategoriaTema categoria);
    Optional<CategoriaTema> findById(Integer id);
    Page<CategoriaTema> filtrar(String search, Boolean ativo, Pageable pageable);
}
