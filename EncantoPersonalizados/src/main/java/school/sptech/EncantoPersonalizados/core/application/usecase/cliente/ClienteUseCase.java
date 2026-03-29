package school.sptech.EncantoPersonalizados.core.application.usecase.cliente;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.CreateClienteDTO;

public interface ClienteUseCase {
    Cliente store(CreateClienteDTO dto);
    Cliente update(CreateClienteDTO dto, Integer id);
    Page<Cliente> getAll(String search, int page);
    void removerPorId(Integer id);
    Cliente findById(Integer id);
}
