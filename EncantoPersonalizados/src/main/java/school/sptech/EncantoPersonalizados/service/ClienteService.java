package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.cliente.ClienteMapper;
import school.sptech.EncantoPersonalizados.dto.cliente.CreateClienteDTO;
import school.sptech.EncantoPersonalizados.dto.cliente.ResponseClienteDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ClienteRepository;
import school.sptech.EncantoPersonalizados.repository.EnderecoClienteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    private final EnderecoClienteRepository enderecoClienteRepository;

    public ClienteService(ClienteRepository repository, EnderecoClienteRepository enderecoClienteRepository) {
        this.repository = repository;
        this.enderecoClienteRepository = enderecoClienteRepository;
    }

    public Cliente store(CreateClienteDTO dto){


        Cliente entity = repository.save(ClienteMapper.toEntity(dto));

        List<EnderecoCliente> enderecoClientes = ClienteMapper.toEntity(dto.enderecos(), entity);

        for(EnderecoCliente e: enderecoClientes){
            enderecoClienteRepository.save(e);
        }

        Optional<Cliente> entityAtualizada = repository.findById(entity.getId());

        if(entityAtualizada.isEmpty()) throw  new EntidadeNaoEncontradaException("Cliente nao encontrado");

        return entityAtualizada.get();
    }

    public Cliente update(CreateClienteDTO dto, Integer id){

        Optional<Cliente> entityAntigaOpt = repository.findById(id);
        if(entityAntigaOpt.isEmpty()) throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        Cliente entityAntiga = entityAntigaOpt.get();

        entityAntiga.setUpdatedAt(LocalDateTime.now());
        entityAntiga.setNome(dto.nome());
        entityAntiga.setTelefone(dto.telefone());

        Cliente entity = repository.save(entityAntiga);
        return entity;
    }

    public Page<Cliente> getAll(String search,  int page){

        Pageable pageable = PageRequest.of(page, 10);
        Page<Cliente> clientes = repository.filtrar(search, pageable);
        return clientes;


    }

    public void removerPorId(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException(
                    "Cliente de id %d não encontrado".formatted(id)
            );
        }
    }



}
