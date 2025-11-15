package school.sptech.EncantoPersonalizados.controller;

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
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.entities.FotoProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.facade.ProdutoFacade;
import school.sptech.EncantoPersonalizados.service.FotoProdutoService;
import school.sptech.EncantoPersonalizados.service.ProdutoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoFacade facade;
    private final ProdutoService service;
    private final FotoProdutoService fotoProdutoService;

    public ProdutoController(ProdutoFacade facade, ProdutoService service, FotoProdutoService fotoProdutoService) {
        this.fotoProdutoService = fotoProdutoService;
        this.facade = facade;
        this.service = service;
    }

    @Operation(description = "Criar um usuario")
    @ApiResponse(responseCode = "201", description = "Cria um produto",
            content =  @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody ProdutoRequestDTO dto
            ){
        ProdutoResponseDTO response = facade.store(dto);
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
        ProdutoResponseDTO response = facade.update(dto, id);
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
            @RequestParam(defaultValue = "0") int page
    ){
        Page<Produto> resposta = service.get(search, categoria, tema, item, ativo, page);
        return ResponseEntity.status(200).body(resposta.map(ProdutoMapper::toDto));
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
        service.mudarEstado(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(description = "Criar foto para o produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Criou a foto do produto"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o produto")
    })
    @PostMapping("/fotos/{produtoId}")
    public ResponseEntity<FotoProdutoResponseDTO> uploadFoto(
            @PathVariable Integer produtoId,
            @RequestParam("foto") MultipartFile file
    ) throws IOException {
        FotoProduto foto = fotoProdutoService.store(produtoId, file);
        FotoProdutoResponseDTO dto = FotoProdutoMapper.toDto(foto);
        return ResponseEntity.status(201).body(dto);
    }

    @Operation(description = "Excluir foto do produto")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Sucesso ao exluir foto"),
            @ApiResponse(responseCode = "404", description = "Não encontrou a foto do produto")
    })
    @DeleteMapping("/fotos/{fotoId}")
    public ResponseEntity<Void> deleteFoto(@PathVariable Integer fotoId) throws IOException {
        fotoProdutoService.deletarFoto(fotoId);
        return ResponseEntity.noContent().build();
    }



}
