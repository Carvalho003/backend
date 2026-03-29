package school.sptech.EncantoPersonalizados.core.application.usecase.enderecoCliente;

import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.EnderecoClienteRequestDTO;

public interface EnderecoClienteUseCase {
    EnderecoCliente store(EnderecoClienteRequestDTO dto, Integer clienteId);
    EnderecoCliente update(EnderecoClienteRequestDTO dto, Integer enderecoId);
    void mudarEstado(Integer id);
}
