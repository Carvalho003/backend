package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.Response;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.core.application.usecase.itemProduto.ItemProdutoUseCase;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemProdutoController {

    private final ItemProdutoUseCase itemProdutoUseCase;

    public ItemProdutoController(ItemProdutoUseCase itemProdutoUseCase) {
        this.itemProdutoUseCase = itemProdutoUseCase;
    }

    @Operation(description = "Cadastra um produto")
    @ApiResponses(
            @ApiResponse(responseCode = "201", description = "Retorna o produto")
    )

    @PostMapping
    public ResponseEntity<ItemProdutoResponseDTO> cadastrar(@RequestBody ItemProdutoRequestDTO item){
        ItemProdutoResponseDTO ProdutoRegistrado = itemProdutoUseCase.cadastrar(item);
        return ResponseEntity.status(201).body(ProdutoRegistrado);
    }

    @Operation(description = "Lista todos os produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos vazia"),
            @ApiResponse(responseCode = "204", description = "Sucesso ao listar, sem conteúdo")
    })
    @GetMapping
    public ResponseEntity<Page<ItemProdutoResponseDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "true") boolean ativo,
            @RequestParam(defaultValue = "0") int page

    ){
        Page<ItemProduto> itens = itemProdutoUseCase.listar(search, ativo, page);

        Page<ItemProdutoResponseDTO> dtos = itens.map(ItemProdutoMapper::toDto);

        return ResponseEntity.status(200).body(dtos);
    }

    @Operation(description = "Lista produtos menor do que o valor especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Retorna a lista de produtos vazia"),
            @ApiResponse(responseCode = "204", description = "Sucesso ao listar, sem conteúdo")
    })
    @GetMapping("/{preco}")
    public ResponseEntity<List<ItemProdutoResponseDTO>> listarPrecoMenorQue(@PathVariable Double preco){
        List<ItemProdutoResponseDTO> itensPrecoMenosQue = itemProdutoUseCase.buscarPorPrecoMenorQue(preco);
        if(!itensPrecoMenosQue.isEmpty()){
            return ResponseEntity.status(200).body(itensPrecoMenosQue);
        }
        return ResponseEntity.status(204).build();
    }

    @Operation(description = "Atualiza produto")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Retorna produto atualizado")
    )
    @PutMapping("/{id}")
    public ResponseEntity<ItemProdutoResponseDTO> updatePorId(
            @RequestBody ItemProduto item,

            @Parameter(description = "Id do produto", example = "1", required = true)
            @PathVariable Integer id
    ){
        ItemProdutoResponseDTO itemAtualizado = itemProdutoUseCase.update(id, item);
        return ResponseEntity.status(200).body(itemAtualizado);
     }

     @Operation(description = "Muda o estado do item")
     @ApiResponses(@ApiResponse(responseCode = "204", description = "Item não encontrado"))
    @DeleteMapping("/{id}")
     public ResponseEntity<Void> excluirPorId(
            @Parameter(description = "Id do produto", example = "1", required = true)
            @PathVariable Integer id
    ){
         itemProdutoUseCase.mudarEstado(id);
         return ResponseEntity.status(204).build();
     }
}
