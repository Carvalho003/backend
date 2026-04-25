package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoArquivoStorageGateway;
import school.sptech.EncantoPersonalizados.core.domain.FotoArquivo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

@Component
public class LocalFotoArquivoStorageAdapter implements FotoArquivoStorageGateway {

    @Value("${upload.directory}")
    private String uploadDir;

    @Async
    @Override
    public CompletableFuture<FotoArquivo> salvar(Integer produtoId, String nomeArquivo, byte[] conteudo) {
        try {
            Path produtoFolder = Paths.get(uploadDir, "produtos", produtoId.toString());
            Files.createDirectories(produtoFolder);

            Path caminhoCompleto = produtoFolder.resolve(nomeArquivo);
            Files.copy(new ByteArrayInputStream(conteudo), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            String caminhoRelativo = "/uploads/produtos/" + produtoId + "/" + nomeArquivo;
            return CompletableFuture.completedFuture(new FotoArquivo(caminhoRelativo, nomeArquivo, produtoId));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Async
    @Override
    public CompletableFuture<String> salvarFotoUsuario(Integer usuarioId, String nomeArquivo, byte[] conteudo) {
        try {
            Path usuarioFolder = Paths.get(uploadDir, "usuarios", usuarioId.toString());
            Files.createDirectories(usuarioFolder);

            Path caminhoCompleto = usuarioFolder.resolve(nomeArquivo);
            Files.copy(new ByteArrayInputStream(conteudo), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            String caminhoRelativo = "/uploads/usuarios/" + usuarioId + "/" + nomeArquivo;
            return CompletableFuture.completedFuture(caminhoRelativo);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Async
    @Override
    public CompletableFuture<Void> deletar(String caminhoRelativo) {
        try {
            Path caminhoArquivo = Paths.get(caminhoRelativo.replace("/uploads", "uploads"));
            Files.deleteIfExists(caminhoArquivo);
            return CompletableFuture.completedFuture(null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}