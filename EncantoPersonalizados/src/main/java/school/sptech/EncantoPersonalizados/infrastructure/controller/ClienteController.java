package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.cliente.*;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;
import school.sptech.EncantoPersonalizados.core.application.usecase.cliente.ClienteUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.enderecoCliente.EnderecoClienteUseCase;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteUseCase clienteUseCase;
    private final EnderecoClienteUseCase enderecoClienteUseCase;

    public ClienteController(ClienteUseCase clienteUseCase, EnderecoClienteUseCase enderecoClienteUseCase) {
        this.clienteUseCase = clienteUseCase;
        this.enderecoClienteUseCase = enderecoClienteUseCase;
    }

    @Operation(description = "Cria um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso ao criar cliente"),
            @ApiResponse(responseCode = "400", description = "Lista não encontrada")
    })
    @PostMapping
    public final ResponseEntity<ResponseClienteDTO> criar (@RequestBody CreateClienteDTO dto){

        if(dto == null) return ResponseEntity.status(400).build();

        Cliente response = clienteUseCase.store(dto);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(response));


    }

    @Operation(description = "Atualiza um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso ao atualizar cliente"),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrada")
    })
    @PutMapping("/{id}")
    public final ResponseEntity<ResponseClienteDTO> update (@RequestBody CreateClienteDTO dto, @PathVariable Integer id){

        if(dto == null) return ResponseEntity.status(400).build();

        Cliente entity = clienteUseCase.update(dto, id);

        ResponseClienteDTO response = ClienteMapper.toDto(entity);
        return ResponseEntity.status(201).body(response);


    }


    @Operation(description = "Lista todos os clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna lista de clientes"),
            @ApiResponse(responseCode = "204", description = "Lista de clientes sem conteúdo")
    })
    @GetMapping()
    public final ResponseEntity<Page<ResponseClienteDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page

    ){

        Page<Cliente> lista = clienteUseCase.getAll(search,  page);
        Page<ResponseClienteDTO> dtos = lista.map(ClienteMapper::toDto);

        return ResponseEntity.status(200).body(dtos);

    }


    @Operation(description = "Cria um endereço de cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso ao criar endereço de  cliente"),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrada")
    })
    @PostMapping("/{id}/enderecos")
    public final ResponseEntity<EnderecoClienteResponseDTO> criarEndereco (
            @RequestBody EnderecoClienteRequestDTO dto,
            @PathVariable Integer id
    ){

        if(dto == null) return ResponseEntity.status(400).build();

        EnderecoCliente response = enderecoClienteUseCase.store(dto, id);
        EnderecoClienteResponseDTO responseDto = ClienteMapper.toDto(response);

        return ResponseEntity.status(201).body(responseDto);


    }

    @Operation(description = "Atualiza um endereço de cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso ao atualizar endereço de  cliente"),
            @ApiResponse(responseCode = "400", description = "Endereco não encontrada")
    })
    @PutMapping("/enderecos/{id}")
    public final ResponseEntity<EnderecoClienteResponseDTO> atualizar (
            @RequestBody EnderecoClienteRequestDTO dto,
            @PathVariable Integer id
    ){

        if(dto == null) return ResponseEntity.status(400).build();

        EnderecoCliente response = enderecoClienteUseCase.update(dto, id);
        EnderecoClienteResponseDTO responseDto = ClienteMapper.toDto(response);

        return ResponseEntity.status(201).body(responseDto);


    }

    @Operation(description = "Muda o estado de um endereco para ativo/inativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao mudar estado do endereco"),
            @ApiResponse(responseCode = "404", description = "Não encontrou categoria")
    })
    @PatchMapping("/enderecos/{id}/mudar-estado")
    public ResponseEntity<Void> mudarEstado(
            @PathVariable Integer id
    ){
        enderecoClienteUseCase.mudarEstado(id);

        return ResponseEntity.status(200).build();
    }
}
