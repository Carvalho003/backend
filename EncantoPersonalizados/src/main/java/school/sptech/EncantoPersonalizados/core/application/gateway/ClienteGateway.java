package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;

public interface ClienteGateway {
    Cliente save(Cliente cliente);
    Cliente findById(Integer id);
    void deleteById(Integer id);
    Page<Cliente> filtrar(String search, Pageable pageable);
}
