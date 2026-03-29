package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.ContraparteGateway;
import school.sptech.EncantoPersonalizados.core.domain.Contraparte;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ContraparteRepository;

import java.util.Optional;

@Repository
public class ContraparteRepositoryAdapter implements ContraparteGateway {

    private final ContraparteRepository repository;

    public ContraparteRepositoryAdapter(ContraparteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contraparte save(Contraparte contraparte) {
        return repository.save(contraparte);
    }

    @Override
    public Optional<Contraparte> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Contraparte> findByIdAndStatusTrue(Integer id) {
        return repository.findByIdAndStatusTrue(id);
    }

    @Override
    public Page<Contraparte> filtrar(String search, String tipo, String segmento, String nome, Boolean status, Pageable pageable) {
        return repository.filtrar(search, tipo, segmento, nome, status, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
