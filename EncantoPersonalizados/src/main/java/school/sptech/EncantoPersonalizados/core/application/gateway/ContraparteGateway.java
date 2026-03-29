package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;

import java.util.Optional;

public interface ContraparteGateway {
    Contraparte save(Contraparte contraparte);
    Optional<Contraparte> findById(Integer id);
    Optional<Contraparte> findByIdAndStatusTrue(Integer id);
    Page<Contraparte> filtrar(String search, String tipo, String segmento, String nome, Boolean status, Pageable pageable);
    void deleteById(Integer id);
}
