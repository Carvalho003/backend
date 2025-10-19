package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.service.CategoriaTemaService;

import java.util.List;

@RestController
@RequestMapping("/categoria-temas")
public class CategoriaTemaController {
    private final CategoriaTemaService service;

    public CategoriaTemaController(CategoriaTemaService service) {
        this.service = service;
    }

    @Operation(description = "Cria um cliente")
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
    public ResponseEntity<List<CategoriaTemaResponseDTO>> listar(){
        List<CategoriaTemaResponseDTO> response = service.listar();
        if(response.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(response);
    }



}
