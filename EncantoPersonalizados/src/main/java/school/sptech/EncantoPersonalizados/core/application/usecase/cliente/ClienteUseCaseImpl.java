package school.sptech.EncantoPersonalizados.core.application.usecase.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.EnderecoClienteGateway;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.ClienteMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.CreateClienteDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ClienteUseCaseImpl implements ClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final EnderecoClienteGateway enderecoClienteGateway;

    public ClienteUseCaseImpl(ClienteGateway clienteGateway, EnderecoClienteGateway enderecoClienteGateway) {
        this.clienteGateway = clienteGateway;
        this.enderecoClienteGateway = enderecoClienteGateway;
    }

    @Override
    public Cliente store(CreateClienteDTO dto) {
        Cliente entity = clienteGateway.save(ClienteMapper.toEntity(dto));

        List<EnderecoCliente> enderecoClientes = ClienteMapper.toEntity(dto.enderecos(), entity);
        for (EnderecoCliente e : enderecoClientes) {
            enderecoClienteGateway.save(e);
        }

        entity.setEnderecoClientes(enderecoClientes);
        return entity;
    }

    @Override
    public Cliente update(CreateClienteDTO dto, Integer id) {
        Optional<Cliente> entityAntigaOpt = Optional.ofNullable(clienteGateway.findById(id));
        if (entityAntigaOpt.isEmpty()) throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        Cliente entityAntiga = entityAntigaOpt.get();

        entityAntiga.setUpdatedAt(LocalDateTime.now());
        entityAntiga.setNome(dto.nome());
        entityAntiga.setTelefone(dto.telefone());

        return clienteGateway.save(entityAntiga);
    }

    @Override
    public Page<Cliente> getAll(String search, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return clienteGateway.filtrar(search, pageable);
    }

    @Override
    public void removerPorId(Integer id) {
        Cliente cliente = clienteGateway.findById(id);
        if (cliente != null) {
            clienteGateway.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException("Cliente de id %d não encontrado".formatted(id));
        }
    }

    @Override
    public Cliente findById(Integer id) {
        return clienteGateway.findById(id);
    }
}
