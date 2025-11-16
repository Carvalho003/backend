package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoMapper;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoReordenacaoDto;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;
import school.sptech.EncantoPersonalizados.service.StatusPedidoService;

import java.util.List;

@RestController
@RequestMapping("/status-pedidos")
public class StatusPedidoController {

    private final StatusPedidoService statusPedidoService;

    public StatusPedidoController(StatusPedidoService statusPedidoService) {
        this.statusPedidoService = statusPedidoService;
    }

    @Operation(description = "Lista todos os status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna lista de status")
    })
    @GetMapping
    public ResponseEntity<Page<StatusPedidoResponseDto>> listar(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "true") boolean ativo
    ){
        Page<StatusPedido> statusPedidoPage = statusPedidoService.listar(page, ativo);
        Page<StatusPedidoResponseDto> dtoPage = statusPedidoPage.map(StatusPedidoMapper::toResponseDto);
        return ResponseEntity.status(200).body(dtoPage);

    }
    // construir endpoint de criar status de pedido
    @Operation(description = "Cria um status de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso ao criar status de pedido")
    })
    @PostMapping
    public ResponseEntity<StatusPedidoResponseDto> criar(@RequestBody StatusPedidoRequestDto dto){
        StatusPedido statusPedido = StatusPedidoMapper.toEntity(dto);
        StatusPedido salvo = statusPedidoService.store(statusPedido);
        StatusPedidoResponseDto responseDto = StatusPedidoMapper.toResponseDto(salvo);
        return ResponseEntity.status(201).body(responseDto);
    }

    @Operation(description = "Atualiza um status de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar status de pedido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StatusPedidoResponseDto> atualizar(@RequestBody StatusPedidoRequestDto dto, @PathVariable Integer id) {
        StatusPedido statusPedido = StatusPedidoMapper.toEntity(dto);
        StatusPedido atualizado = statusPedidoService.update(statusPedido, id);
        StatusPedidoResponseDto responseDto = StatusPedidoMapper.toResponseDto(atualizado);
        return ResponseEntity.status(200).body(responseDto);
    }

    @Operation(description = "Desativa um status de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao desativar status de pedido")
    })
    @PatchMapping("/mudar-estado/{id}")
    public ResponseEntity<Void> mudarEstado(@PathVariable Integer id) {
        statusPedidoService.mudarEstado(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(description = "Reordenar o quadro Kanban")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao reordenar o quadro Kanban")
    })
    @PostMapping("/reordenar-kanban")
    public ResponseEntity<Void> reordenarKanban(@RequestBody List<StatusPedidoReordenacaoDto> novosIdsNaOrdem) {
        statusPedidoService.reordenarKanban(novosIdsNaOrdem);
        return ResponseEntity.status(200).build();
    }
}
