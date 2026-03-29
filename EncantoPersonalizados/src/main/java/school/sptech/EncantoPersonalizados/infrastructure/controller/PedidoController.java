package school.sptech.EncantoPersonalizados.infrastructure.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;
import school.sptech.EncantoPersonalizados.core.application.usecase.pedido.PedidoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.produtoPedido.ProdutoPedidoUseCase;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoUseCase pedidoUseCase;
    private final ProdutoPedidoUseCase produtoPedidoUseCase;

    public PedidoController(PedidoUseCase pedidoUseCase, ProdutoPedidoUseCase produtoPedidoUseCase) {
        this.pedidoUseCase = pedidoUseCase;
        this.produtoPedidoUseCase = produtoPedidoUseCase;
    }

    @Operation(description = "Criar um novo pedido")
    @ApiResponse(responseCode = "201", description = "Cria um pedido",
            content =  @Content(schema = @Schema(implementation = PedidoCreatedResponseDto.class)))
    @PostMapping
    public ResponseEntity<PedidoCreatedResponseDto> criar(
            @RequestBody PedidoRequestDto dto
    ){
        PedidoCreatedResponseDto response = pedidoUseCase.store(dto);


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
        Page<PedidoResponseDto> response = pedidoUseCase.listar(search, page, ativa);
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
        PedidoResponseDto response = pedidoUseCase.getById(id);
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
        PedidoCreatedResponseDto response = pedidoUseCase.update(id, dto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "muda o estado do pedido ativo/inativo")
    @ApiResponse(responseCode = "200", description = "Mudou o estado do pedido",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @PatchMapping("/mudar-estado/{id}")
    public ResponseEntity<PedidoCreatedResponseDto> mudarEstado(
            @PathVariable Integer id
    ){
        pedidoUseCase.mudarEstado(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Muda o status atual do pedido")
    @ApiResponse(responseCode = "200", description = "Mudou os status do pedido",
            content =  @Content(schema = @Schema(implementation = PedidoResponseDto.class)))
    @PatchMapping("/mudar-status")
    public ResponseEntity<PedidoCreatedResponseDto> mudarStatus(
            @RequestBody PedidoStatusPedidoRequestDto dto
            ){
        pedidoUseCase.mudarStatus(dto);
        return ResponseEntity.status(200).build();
    }

    @Operation(description = "Adicionar novo produto a um pedido")
    @ApiResponse(responseCode = "200", description = "Adicionou novo produto ao pedido",
            content =  @Content(schema = @Schema(implementation = ProdutosPedidoResponseDto.class)))
    @PostMapping("/produtos")
    public ResponseEntity<ProdutosPedidoResponseDto> criarProdutoParaOPedido(
            @RequestBody ProdutosPedidoRequestDto dto
    ){
        ProdutoPedido produtoPedido = ProdutosPedidoMapper.toEntity(dto);



        ProdutoPedido produtoPedidoSalvo = produtoPedidoUseCase.salvar(produtoPedido, dto.idProduto(), dto.idPedido());

        pedidoUseCase.atualizarPrecoPesoDoPedido(true, produtoPedidoSalvo.getPrecoTotal(), produtoPedidoSalvo.getPesoTotal(), dto.idPedido());


        ProdutosPedidoResponseDto response =
                new ProdutosPedidoResponseDto(
                        produtoPedidoSalvo.getId(),
                        dto.idProduto(),
                        dto.idPedido(),
                        produtoPedidoSalvo.getQtdProduto(),
                        produtoPedidoSalvo.getPesoTotal(),
                        produtoPedidoSalvo.getPesoUnitario(),
                        produtoPedidoSalvo.getPrecoUnitario(),
                        produtoPedidoSalvo.getPrecoTotal(),
                        produtoPedidoSalvo.getCreatedAt(),
                        produtoPedidoSalvo.getUpdatedAt()
                );
        return ResponseEntity.status(200).body(response);

    }

    @Operation(description = "Remover produto de um pedido")
    @ApiResponse(responseCode = "200", description = "removeu o produto do pedido")
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> removerProdutoDeUmPedido(
            @PathVariable Integer id
    ){
        ProdutoPedido produtoPedido = produtoPedidoUseCase.removerProduto(id);
        pedidoUseCase.atualizarPrecoPesoDoPedido(false, produtoPedido.getPrecoTotal(), produtoPedido.getPesoTotal(), produtoPedido.getPedido().getId());

        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Atualizar produto de um pedido")
    @ApiResponse(responseCode = "200", description = "Atualizou o produto do pedido")
    @PutMapping("/produtos/{id}")
    public ResponseEntity<Void> atualizarProdutoDeUmPedido(
            @PathVariable Integer id,
            @RequestBody ProdutosPedidoRequestDto dto
    ){


        List<?> novosTotais = produtoPedidoUseCase.atualizarProduto(id, dto.quantidade());
        Double pesoAdicionar = (Double) novosTotais.get(0);
        Double precoAdicionar = (Double) novosTotais.get(1);
        Integer pedidoId = (Integer) novosTotais.get(2);

        pedidoUseCase.atualizarPrecoPesoDoPedido(true, precoAdicionar, pesoAdicionar, pedidoId);

        return ResponseEntity.status(204).build();
    }

    // endpoints, criar novo produto para o pedido, atualizar pedido existente no produto
    // e deletar produto do pedido



}
