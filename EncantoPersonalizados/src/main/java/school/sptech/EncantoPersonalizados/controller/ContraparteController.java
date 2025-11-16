package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.entities.Contraparte;
import school.sptech.EncantoPersonalizados.service.ContraparteService;

@RestController
@RequestMapping("contrapartes")
public class ContraparteController {
    private final ContraparteService service;

    public ContraparteController(ContraparteService service) {
        this.service = service;
    }

    @Operation(description = "Criar contraparte")
    @ApiResponse(responseCode = "201", description = "Sucesso ao criar contraparte")
    @ApiResponse(responseCode = "400", description = "Falha ao criar contraparte")
    @PostMapping
    public ResponseEntity<ResponseContraparteDTO> create(
            @RequestBody @Valid RequestContraparteDTO dto
    ){
        if (dto == null) {
            return ResponseEntity.status(400).build();
        }

        ResponseContraparteDTO response = service.create(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Buscar Contrapartes")
    @ApiResponse(responseCode = "200", description = "Sucesso ao buscar contrapartes")
    @ApiResponse(responseCode = "204", description = "Sem contrapartes para retornar")
    @GetMapping
    public ResponseEntity<Page<ResponseContraparteDTO>> get(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String segmento,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") int page
    ){
        Page<ResponseContraparteDTO> lista = service.get(nome, search, segmento, tipo, status, page);
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(description = "Buscar por id")
    @ApiResponse(responseCode = "200", description = "Sucesso ao buscar contraparte")
    @ApiResponse(responseCode = "204", description = "Sem contraparte para retornar")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContraparteDTO> getById(
            @PathVariable Integer id
    ) {
        ResponseContraparteDTO response = service.getById(id);

        if (response == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Atualizar contraparte")
    @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContraparteDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid RequestContraparteDTO dto
    ){
        ResponseContraparteDTO response = service.update(id, dto);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Deletar contraparte")
    @ApiResponse(responseCode = "204", description = "Sucesso ao deletar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ){
        var delete = service.delete(id);

        return ResponseEntity.status(204).build();
    }

}
