package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.ClienteRepository;

@Repository
public class ClienteRepositoryAdapter implements ClienteGateway {

    private final ClienteRepository repository;

    public ClienteRepositoryAdapter(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Cliente> filtrar(String search, Pageable pageable) {
        return repository.filtrar(search, pageable);
    }
}
