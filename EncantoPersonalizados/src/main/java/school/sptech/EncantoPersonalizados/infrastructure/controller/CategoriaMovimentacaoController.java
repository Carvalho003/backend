package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.core.application.usecase.categoriaMovimentacao.CategoriaMovimentacaoUseCase;

import java.util.List;

@RestController
@RequestMapping("/categoria-movimentacoes")
public class CategoriaMovimentacaoController {
    private final CategoriaMovimentacaoUseCase categoriaMovimentacaoUseCase;

    public CategoriaMovimentacaoController(CategoriaMovimentacaoUseCase categoriaMovimentacaoUseCase) {
        this.categoriaMovimentacaoUseCase = categoriaMovimentacaoUseCase;
    }

    @Operation(description = "Criar categoria de movimentação")
    @ApiResponse(responseCode = "201", description = "Sucesso ao criar categoria")
    @PostMapping
    public ResponseEntity<ResponseCategoriaMovimentacaoDTO> create(
            @Valid @RequestBody RequestCategoriaMovimentacaoDTO dto
    ) {
        ResponseCategoriaMovimentacaoDTO response = categoriaMovimentacaoUseCase.create(dto);

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
        Page<CategoriaMovimentacao> resposta = categoriaMovimentacaoUseCase.get(search, status, page);
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

        ResponseCategoriaMovimentacaoDTO response = categoriaMovimentacaoUseCase.findById(id);

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
        ResponseCategoriaMovimentacaoDTO response = categoriaMovimentacaoUseCase.update(id, dto);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Deletar categoria de movimentação")
    @ApiResponse(responseCode = "204", description = "Delete realizado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        categoriaMovimentacaoUseCase.delete(id);
        return ResponseEntity.status(204).build();
    }
}
