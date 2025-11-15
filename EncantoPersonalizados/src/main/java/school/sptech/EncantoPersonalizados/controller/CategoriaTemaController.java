package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaMapper;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.service.CategoriaTemaService;

import java.util.List;

@RestController
@RequestMapping("/categoria-temas")
public class CategoriaTemaController {
    private final CategoriaTemaService service;

    public CategoriaTemaController(CategoriaTemaService service) {
        this.service = service;
    }

    @Operation(description = "Cria uma categoria de tema")
    @ApiResponse(responseCode = "200", description = "Sucesso ao criar categoria")
    @PostMapping
    public ResponseEntity<CategoriaTemaResponseDTO> criar(@Valid @RequestBody CategoriaTemaRequestDTO dto){
        CategoriaTemaResponseDTO response = service.criar(dto);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Listar todos as categorias de temas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de categorias",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = CategoriaTemaResponseDTO.class)))),
            @ApiResponse(responseCode = "204", description = "Não encontrou categorias")
    })
    @GetMapping
    public ResponseEntity<Page<CategoriaTemaResponseDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "true") boolean ativo
    ){
        Page<CategoriaTema> response = service.listar(search, ativo, page);
        Page<CategoriaTemaResponseDTO> dtos = response.map(CategoriaTemaMapper::toDto);
        return ResponseEntity.status(200).body(dtos);
    }

    @Operation(description = "Atualiza uma categoria de tema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar categoria"),
            @ApiResponse(responseCode = "404", description = "Não encontrou a categoria")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaTemaResponseDTO> update(
            @Valid @RequestBody CategoriaTemaRequestDTO dto,
            @PathVariable Integer id
    ){
        CategoriaTemaResponseDTO response = service.update(dto, id);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Muda o estado de uma categoria para ativo/inativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao mudar estado da categoria"),
            @ApiResponse(responseCode = "404", description = "Não encontrou categoria")
    })
    @PatchMapping("mudar-estado/{id}")
    public ResponseEntity<Void> mudarEstado(
            @PathVariable Integer id
    ){
        service.mudarEstado(id);

        return ResponseEntity.status(200).build();
    }



}
