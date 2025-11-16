package school.sptech.EncantoPersonalizados.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.facade.PedidoFacade;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoFacade facade;

    public PedidoController(PedidoFacade facade) {
        this.facade = facade;
    }

    @Operation(description = "Criar um novo pedido")
    @ApiResponse(responseCode = "201", description = "Cria um pedido",
            content =  @Content(schema = @Schema(implementation = PedidoCreatedResponseDto.class)))
    @PostMapping
    public ResponseEntity<PedidoCreatedResponseDto> criar(
            @RequestBody PedidoRequestDto dto
    ){
        PedidoCreatedResponseDto response = facade.store(dto);


        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Lista todos os pedidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos",
                    content =  @Content(array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = PedidoResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDto>> listar(
            @RequestParam(defaultValue = "true") Boolean ativa,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") Integer page
    )
    {
        Page<PedidoResponseDto> response = facade.listar(search, page, ativa);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Busca um pedido por id")
    @ApiResponse(responseCode = "201", description = "Cria um pedido",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getById(
        @PathVariable Integer id
    )
    {
        PedidoResponseDto response = facade.getById(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Atualiza o pedido")
    @ApiResponse(responseCode = "200", description = "Pedido atualizado",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @PutMapping("/{id}")
    public ResponseEntity<PedidoCreatedResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody PedidoRequestDto dto
    ){
        PedidoCreatedResponseDto response = facade.update(id, dto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "muda o estado do pedido ativo/inativo")
    @ApiResponse(responseCode = "200", description = "Mudou o estado do pedido",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @PatchMapping("/mudar-estado/{id}")
    public ResponseEntity<PedidoCreatedResponseDto> mudarEstado(
            @PathVariable Integer id
    ){
        facade.mudarEstado(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Muda o status atual do pedido")
    @ApiResponse(responseCode = "200", description = "Mudou os status do pedido",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @PatchMapping("/mudar-status")
    public ResponseEntity<PedidoCreatedResponseDto> mudarStatus(
            @RequestBody PedidoStatusPedidoRequestDto dto
            ){
        facade.mudarStatus(dto);
        return ResponseEntity.status(200).build();
    }

    // endpoints, criar novo produto para o pedido, atualizar pedido existente no produto
    // e deletar produto do pedido



}
