package school.sptech.EncantoPersonalizados.core.application.usecase.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.EnderecoClienteGateway;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeConflitoException;
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
        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setAtivo(true);
        Cliente entity = clienteGateway.save(cliente);
        List<EnderecoCliente> enderecoClientes = ClienteMapper.toEntity(dto.enderecos(), entity);
        for (EnderecoCliente e : enderecoClientes) {
            enderecoClienteGateway.save(e);
        }

        entity.setEnderecoClientes(enderecoClientes);
        return entity;
    }

    @Override
    public Cliente update(CreateClienteDTO dto, Integer id) {
        Cliente clienteExistente = clienteGateway.findById(id);
        if (clienteExistente == null) {
            throw new EntidadeNaoEncontradaException("Cliente de id %d não encontrado".formatted(id));
        }
        if (clienteExistente.getAtivo() != null && !clienteExistente.getAtivo()) {
            throw new EntidadeConflitoException("Não é possível alterar um cliente inativo.");
        }
        clienteExistente.setNome(dto.nome());
        clienteExistente.setTelefone(dto.telefone());
        clienteExistente.setUpdatedAt(LocalDateTime.now());

        return clienteGateway.save(clienteExistente);
    }

    @Override
    public Page<Cliente> getAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteGateway.filtrar(search, pageable);
    }

    @Override
    public void removerPorId(Integer id) {
        Cliente cliente = clienteGateway.findById(id);
        if (cliente != null) {
            cliente.setAtivo(false);
            cliente.setUpdatedAt(LocalDateTime.now());
            clienteGateway.save(cliente);
        } else {
            throw new EntidadeNaoEncontradaException("Cliente de id %d não encontrado".formatted(id));
        }
    }

    @Override
    public Cliente findById(Integer id) {
        return clienteGateway.findById(id);
    }
}
