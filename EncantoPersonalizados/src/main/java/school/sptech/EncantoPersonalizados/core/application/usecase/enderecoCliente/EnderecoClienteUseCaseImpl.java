package school.sptech.EncantoPersonalizados.core.application.usecase.enderecoCliente;

import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.EnderecoClienteGateway;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.core.domain.exception.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.ClienteMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.EnderecoClienteRequestDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EnderecoClienteUseCaseImpl implements EnderecoClienteUseCase {

    private final EnderecoClienteGateway enderecoClienteGateway;
    private final ClienteGateway clienteGateway;

    public EnderecoClienteUseCaseImpl(EnderecoClienteGateway enderecoClienteGateway, ClienteGateway clienteGateway) {
        this.enderecoClienteGateway = enderecoClienteGateway;
        this.clienteGateway = clienteGateway;
    }

    @Override
    public EnderecoCliente store(EnderecoClienteRequestDTO dto, Integer clienteId) {
        Cliente cliente = clienteGateway.findById(clienteId);
        if (cliente == null) throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        EnderecoCliente entity = ClienteMapper.toEntity(dto, cliente);
        return enderecoClienteGateway.save(entity);
    }

    @Override
    public EnderecoCliente update(EnderecoClienteRequestDTO d, Integer enderecoId) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteGateway.findById(enderecoId);
        if (enderecoCliente.isEmpty()) throw new EntidadeNaoEncontradaException("Endereco não encontrado");

        EnderecoCliente e = enderecoCliente.get();
        e.setLogradouro(d.logradouro());
        e.setNumLogradouro(d.numLogradouro());
        e.setBairro(d.bairro());
        e.setCep(d.cep());
        e.setUf(d.uf());
        e.setCidade(d.cidade());
        e.setEstado(d.estado());
        e.setMunicipio(d.municipio());
        e.setComplemento(d.complemento());
        e.setCreatedAt(LocalDateTime.now());
        return enderecoClienteGateway.save(e);
    }

    @Override
    public void mudarEstado(Integer id) {
        Optional<EnderecoCliente> entityOpt = enderecoClienteGateway.findById(id);
        if (entityOpt.isEmpty()) throw new CategoriaTemaNaoEncontradaException("Categoria de tema não encontrado");
        EnderecoCliente entity = entityOpt.get();
        entity.setAtivo(!entity.getAtivo());
        enderecoClienteGateway.save(entity);
    }
}
