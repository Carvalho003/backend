package school.sptech.EncantoPersonalizados.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.service.TemaProdutoService;

import java.util.List;

@RestController
@RequestMapping("/temas")
public class TemaProdutoController {
    public final TemaProdutoService service;

    public TemaProdutoController(TemaProdutoService service) {
        this.service = service;
    }
    @Operation(description = "Listar todos os temas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de temas",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = UsuarioResponseDTO.class)))),
            @ApiResponse(responseCode = "204", description = "Não encontrou usuários")
    })
    @GetMapping
    public ResponseEntity<Page<TemaProdutoResponseDTO>> get(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "true") boolean ativo
    ){
        Page<TemaProduto> temas = service.get(search, categoria, page, ativo);
        Page<TemaProdutoResponseDTO> dtos = temas.map(TemaProdutoMapper::toDto);

        return ResponseEntity.status(200).body(dtos);
    }

    @Operation
    @ApiResponse(responseCode = "201", description = "Cria um tema",
            content = @Content(schema =  @Schema(implementation = TemaProdutoResponseDTO.class)))
    @PostMapping
    public ResponseEntity<TemaProdutoResponseDTO> store(
            @RequestBody @Valid TemaProdutoRequestDTO dto
            )
    {
        TemaProduto response = service.store(dto);
        return ResponseEntity.status(201).body(TemaProdutoMapper.toDto(response));
    }

    @Operation
    @ApiResponse(responseCode = "201", description = "Atualiza um tema",
            content = @Content(schema =  @Schema(implementation = TemaProdutoResponseDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<TemaProdutoResponseDTO> update(
            @RequestBody @Valid TemaProdutoRequestDTO dto,
            @PathVariable Integer id
    )
    {
        TemaProduto response = service.update(dto, id);
        return ResponseEntity.status(201).body(TemaProdutoMapper.toDto(response));
    }

    @Operation(description = "Muda o estado de um tema para ativo/inativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao mudar estado do tema"),
            @ApiResponse(responseCode = "404", description = "Não encontrou tema")
    })
    @PatchMapping("mudar-estado/{id}")
    public ResponseEntity<Void> mudarEstado(
            @PathVariable Integer id
    ){
        service.mudarEstado(id);

        return ResponseEntity.status(200).build();
    }
}
