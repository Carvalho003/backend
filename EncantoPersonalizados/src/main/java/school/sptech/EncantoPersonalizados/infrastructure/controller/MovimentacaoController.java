package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.MapperMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.core.application.usecase.movimentacao.MovimentacaoUseCase;

import javax.print.DocFlavor;
import java.time.LocalDate;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {
    private final MovimentacaoUseCase movimentacaoUseCase;

    public MovimentacaoController(MovimentacaoUseCase movimentacaoUseCase) {
        this.movimentacaoUseCase = movimentacaoUseCase;
    }

    @Operation(description = "Criar movimentação")
    @ApiResponse(responseCode = "201", description = "Sucesso ao criar movimentação")
    @PostMapping
    public ResponseEntity<ResponseMovimentacaoDTO> create(
            @Valid @RequestBody RequestMovimentacaoDTO dto
    ) {
        ResponseMovimentacaoDTO response = movimentacaoUseCase.create(dto);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Buscar movimentações")
    @ApiResponse(responseCode = "200", description = "Sucesso ao buscar movimentações")
    @GetMapping
    public ResponseEntity<Page<ResponseMovimentacaoDTO>> get(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Double valor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String contraparte,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String statusPagamento,
            @RequestParam(required = false) LocalDate dataVencInicio,
            @RequestParam(required = false) LocalDate dataVencFim,
            @RequestParam(required = false) LocalDate dataPagInicio,
            @RequestParam(required = false) LocalDate dataPagFim,
            @RequestParam(defaultValue = "0") int page
    ){
        Page<ResponseMovimentacaoDTO> resposta = movimentacaoUseCase.get(search, tipo, valor, categoria, contraparte, nome, status, statusPagamento,
                dataVencInicio, dataVencFim, dataPagInicio, dataPagFim, page);

        if (resposta.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(description = "Buscar por id")
    @ApiResponse(responseCode = "200", description = "Sucesso na busca da movimentação")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMovimentacaoDTO> getById(
            @PathVariable Integer id
    ){
        ResponseMovimentacaoDTO response = movimentacaoUseCase.getById(id);

        if (response == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Atualizar movimentação")
    @ApiResponse(responseCode = "200", description = "Movimentação atualizada")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMovimentacaoDTO> update(
           @PathVariable Integer id,
           @RequestBody RequestMovimentacaoDTO dto
    ) {
        ResponseMovimentacaoDTO response = movimentacaoUseCase.update(id, dto);

        if (response == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @Operation(description = "Deletar movimentação")
    @ApiResponse(responseCode = "204", description = "Deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ){
        if (movimentacaoUseCase.delete(id)) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
