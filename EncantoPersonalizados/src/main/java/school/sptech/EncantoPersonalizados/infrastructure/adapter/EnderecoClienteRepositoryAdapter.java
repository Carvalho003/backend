package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.EnderecoClienteGateway;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.EnderecoClienteRepository;

import java.util.Optional;

@Repository
public class EnderecoClienteRepositoryAdapter implements EnderecoClienteGateway {

    private final EnderecoClienteRepository repository;

    public EnderecoClienteRepositoryAdapter(EnderecoClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public EnderecoCliente save(EnderecoCliente endereco) {
        return repository.save(endereco);
    }

    @Override
    public Optional<EnderecoCliente> findById(Integer id) {
        return repository.findById(id);
    }
}
