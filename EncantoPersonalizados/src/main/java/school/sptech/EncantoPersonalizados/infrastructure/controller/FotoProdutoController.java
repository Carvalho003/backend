package school.sptech.EncantoPersonalizados.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.ArmazenarFotoProdutoUseCase;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/produtos")
public class FotoProdutoController {

    private final ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase;

    public FotoProdutoController(ArmazenarFotoProdutoUseCase armazenarFotoProdutoUseCase) {
        this.armazenarFotoProdutoUseCase = armazenarFotoProdutoUseCase;
    }

    @PostMapping("/{id}/fotos")
    public CompletableFuture<ResponseEntity<FotoProdutoResponseDTO>> adicionarFoto(
            @PathVariable Integer id,
            @RequestParam MultipartFile file) throws IOException {
        return armazenarFotoProdutoUseCase.store(id, file)
                .thenApply(foto -> ResponseEntity.status(201).body(FotoProdutoMapper.toDto(foto)));
    }

    @DeleteMapping("/{id}/fotos/{fotoId}")
    public CompletableFuture<ResponseEntity<Void>> removerFoto(
            @PathVariable Integer id,
            @PathVariable Integer fotoId) {
        return armazenarFotoProdutoUseCase.deletarFoto(fotoId)
                .thenApply(v -> ResponseEntity.<Void>noContent().build());
    }
}
