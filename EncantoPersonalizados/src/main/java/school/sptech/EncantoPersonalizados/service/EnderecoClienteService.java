package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.cliente.ClienteMapper;
import school.sptech.EncantoPersonalizados.dto.cliente.EnderecoClienteRequestDTO;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ClienteRepository;
import school.sptech.EncantoPersonalizados.repository.EnderecoClienteRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EnderecoClienteService{
    private final EnderecoClienteRepository repository;
    private final ClienteRepository clienteRepository;

    public EnderecoClienteService(EnderecoClienteRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public EnderecoCliente store(EnderecoClienteRequestDTO dto, Integer clienteId){
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if(cliente.isEmpty()) throw  new EntidadeNaoEncontradaException("Cliente não encontrado");
        EnderecoCliente entity = ClienteMapper.toEntity(dto, cliente.get());
        return repository.save(entity);


    }

    public EnderecoCliente update(EnderecoClienteRequestDTO d, Integer enderecoId){
        Optional<EnderecoCliente> enderecoCliente = repository.findById(enderecoId);
        if(enderecoCliente.isEmpty()) throw  new EntidadeNaoEncontradaException("Endereco não encontrado");

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
        return repository.save(e);


    }

    public void mudarEstado(Integer id)
    {
        Optional<EnderecoCliente> entityOpt = repository.findById(id);
        if(entityOpt.isEmpty()) throw new CategoriaTemaNaoEncontradaException("Categoria de tema não encontrado");
        EnderecoCliente entity = entityOpt.get();
        if(entity.getAtivo()){
            entity.setAtivo(false);
        }else{
            entity.setAtivo(true);
        }
        repository.save(entity);
    }
}