package school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto;

import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ArmazenarFotoProdutoUseCase {
    CompletableFuture<FotoProduto> store(Integer produtoId, MultipartFile file) throws IOException;
    CompletableFuture<Void> deletarFoto(Integer fotoId);
}
