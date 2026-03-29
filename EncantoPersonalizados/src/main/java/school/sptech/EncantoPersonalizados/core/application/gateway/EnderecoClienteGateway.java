package school.sptech.EncantoPersonalizados.core.application.gateway;

import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;

import java.util.Optional;

public interface EnderecoClienteGateway {
    EnderecoCliente save(EnderecoCliente endereco);
    Optional<EnderecoCliente> findById(Integer id);
}
