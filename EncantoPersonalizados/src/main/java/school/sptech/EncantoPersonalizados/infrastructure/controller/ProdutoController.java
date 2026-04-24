package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.application.usecase.produto.ProdutoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.ArmazenarFotoProdutoUseCase;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoUseCase produtoUseCase;
    private final ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase;

    public ProdutoController(ProdutoUseCase produtoUseCase, ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase) {
        this.produtoUseCase = produtoUseCase;
        this.armazenarFotoProdutoUseCase = armazenarFotoProdutoUseCase;
    }

    @Operation(description = "Criar um produto")
    @ApiResponse(responseCode = "201", description = "Cria um produto",
            content =  @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody ProdutoRequestDTO dto
            ){
        ProdutoResponseDTO response = produtoUseCase.storeFull(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualiza um produto",
                    content =  @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @RequestBody ProdutoRequestDTO dto,
            @PathVariable Integer id
    ){
        ProdutoResponseDTO response = produtoUseCase.update(dto, id);
        return ResponseEntity.status(201).body(response);
    }




    @Operation(description = "Listar todos os produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = ProdutoResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> get(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tema,
            @RequestParam(required = false) String item,
            @RequestParam(defaultValue = "true") boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Produto> resposta = produtoUseCase.get(search, categoria, tema, item, ativo, page, size);
        return ResponseEntity.status(200).body(resposta.map(ProdutoMapper::toDto));
    }

    @Operation(description = "Trazer produto especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o produto especifico pelo ID",
                    content =  @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getById(
            @PathVariable Integer id
    ){
        Produto resposta = produtoUseCase.findById(id);
        if(resposta == null) throw new ProdutoNaoEncontradoException("Produto não encotrado");
        return ResponseEntity.status(200).body(ProdutoMapper.toDto(resposta));
    }

    @Operation(description = "Mudar estado ativo e inativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao mudar estado"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o produto")
    })
    @PatchMapping("/mudar-estado/{id}")
    public ResponseEntity<Void> mudarEstado(
            @PathVariable Integer id
    )
    {
        produtoUseCase.mudarEstado(id);
        return ResponseEntity.status(200).build();
    }

//    @Operation(description = "Criar foto para o produto")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Criou a foto do produto"),
//            @ApiResponse(responseCode = "404", description = "Não encontrou o produto")
//    })
//    @PostMapping("/{produtoId}/fotos")
//    public CompletableFuture<ResponseEntity<FotoProdutoResponseDTO>> uploadFoto(
//            @PathVariable Integer produtoId,
//            @RequestParam("foto") MultipartFile file
//    ) throws IOException {
//        return armazenarFotoProdutoUseCase.store(produtoId, file)
//                .thenApply(foto -> ResponseEntity.status(201).body(FotoProdutoMapper.toDto(foto)));
//    }
//
//    @Operation(description = "Excluir foto do produto")
//    @ApiResponses({
//            @ApiResponse(responseCode = "202", description = "Sucesso ao exluir foto"),
//            @ApiResponse(responseCode = "404", description = "Não encontrou a foto do produto")
//    })
//    @DeleteMapping("/fotos/{fotoId}")
//    public CompletableFuture<ResponseEntity<Void>> deleteFoto(@PathVariable Integer fotoId) {
//        return armazenarFotoProdutoUseCase.deletarFoto(fotoId)
//                .thenApply(v -> ResponseEntity.<Void>noContent().build());
//    }



}
