package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioMapper;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.service.CategoriaMovimentacaoService;

import java.util.List;

@RestController
@RequestMapping("/categoria-movimentacao")
public class CategoriaMovimentacaoController {
    private final CategoriaMovimentacaoService service;

    public CategoriaMovimentacaoController(CategoriaMovimentacaoService service) {
        this.service = service;
    }

    @Operation(description = "Criar categoria de movimentação")
    @ApiResponse(responseCode = "200", description = "Sucesso ao criar categoria")
    @PostMapping
    public ResponseEntity<ResponseCategoriaMovimentacaoDTO> create(
            @Valid @RequestBody RequestCategoriaMovimentacaoDTO dto
    ) {
        ResponseCategoriaMovimentacaoDTO response = service.create(dto);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Buscar categorias de movimentação")
    @ApiResponse(responseCode = "200", description = "Listar todas as categorias")
    @GetMapping
    public ResponseEntity<Page<ResponseCategoriaMovimentacaoDTO>> get(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<CategoriaMovimentacao> resposta = service.get(search, status, page);
        if(!resposta.isEmpty()) {
            return ResponseEntity.status(200).body(resposta.map((MapperCategoriaMovimentacao::toDTO)));
        }

        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Buscar categoria por id")
    @ApiResponse(responseCode = "200", description = "Categoria buscada")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoriaMovimentacaoDTO> getById(
            @PathVariable Integer id
    ){
        if (id == null) {
            return ResponseEntity.status(400).build();
        }

        ResponseCategoriaMovimentacaoDTO response = service.findById(id);

        if (response != null) {
            return ResponseEntity.status(200).body(response);
        }

        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Atualização de categoria de movimentação")
    @ApiResponse(responseCode = "200", description = "Atualização realizada")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoriaMovimentacaoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid RequestCategoriaMovimentacaoDTO dto
    ){
        ResponseCategoriaMovimentacaoDTO response = service.update(id, dto);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Deletar categoria de movimentação")
    @ApiResponse(responseCode = "204", description = "Delete realizado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.status(204).build();
    }
}
