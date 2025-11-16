package school.sptech.EncantoPersonalizados.dto.cliente;

import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;

import java.time.LocalDateTime;
import java.util.List;

public class ClienteMapper {

    public static Cliente toEntity(CreateClienteDTO dto){
        if (dto == null)return null;

        Cliente entity = new Cliente();
        entity.setNome(dto.nome());
        entity.setTelefone(dto.telefone());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;


    }

    public static EnderecoCliente toEntity(EnderecoClienteRequestDTO d, Cliente cliente){
        if (d == null)return null;

        EnderecoCliente e = new EnderecoCliente();
        e.setCliente(cliente);
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
        return e;


    }

    public static List<EnderecoCliente> toEntity(List<EnderecoClienteRequestDTO> dto, Cliente cliente){
        if (dto == null)return null;

        List<EnderecoCliente> entity =
                dto.stream()
                        .map(d -> {
                            EnderecoCliente e = new EnderecoCliente();
                            e.setCliente(cliente);
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
                            return e;
                        })
                        .toList();

        return entity;


    }

    public static EnderecoClienteResponseDTO toDto(EnderecoCliente ee){

        if (ee == null) return null;

        EnderecoClienteResponseDTO dto = new EnderecoClienteResponseDTO(
                ee.getId(),
                ee.getLogradouro(),
                ee.getNumLogradouro(),
                ee.getBairro(),
                ee.getCep(),
                ee.getUf(),
                ee.getCidade(),
                ee.getEstado(),
                ee.getMunicipio(),
                ee.getComplemento(),
                ee.getCreatedAt(),
                ee.getUpdatedAt()
        );
        return dto;



    }


    public static ResponseClienteDTO toDto(Cliente entity){

        if (entity == null) return null;

        List<EnderecoClienteResponseDTO> dtosEnderecos =
                entity.getEnderecoClientes().stream()
                        .map(ee -> {
                            EnderecoClienteResponseDTO dto = new EnderecoClienteResponseDTO(
                                    ee.getId(),
                                    ee.getLogradouro(),
                                    ee.getNumLogradouro(),
                                    ee.getBairro(),
                                    ee.getCep(),
                                    ee.getUf(),
                                    ee.getCidade(),
                                    ee.getEstado(),
                                    ee.getMunicipio(),
                                    ee.getComplemento(),
                                    ee.getCreatedAt(),
                                    ee.getUpdatedAt()
                            );
                            return dto;
                        })
                        .toList();

        ResponseClienteDTO dto = new ResponseClienteDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                dtosEnderecos,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
                );


        return dto;
    }

    public static List<ResponseClienteDTO> toDto(List<Cliente> entity){

        if (entity == null) return null;

     return entity.stream()
                .map(e -> {

                    List<EnderecoClienteResponseDTO> dtosEnderecos =
                            e.getEnderecoClientes().stream()
                                    .map(ee -> {
                                        EnderecoClienteResponseDTO dto = new EnderecoClienteResponseDTO(
                                                ee.getId(),
                                                ee.getLogradouro(),
                                                ee.getNumLogradouro(),
                                                ee.getBairro(),
                                                ee.getCep(),
                                                ee.getUf(),
                                                ee.getCidade(),
                                                ee.getEstado(),
                                                ee.getMunicipio(),
                                                ee.getComplemento(),
                                                ee.getCreatedAt(),
                                                ee.getUpdatedAt()
                                        );
                                        return dto;
                                    })
                                    .toList();

                    ResponseClienteDTO dto = new ResponseClienteDTO(
                            e.getId(),
                            e.getNome(),
                            e.getTelefone(),
                            dtosEnderecos,
                            e.getCreatedAt(),
                            e.getUpdatedAt()
                    );
                    return dto;
                })

                .toList();


    }

}
