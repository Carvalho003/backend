package school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORTANTE
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoArquivoStorageGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class ArmazenarFotoProdutoUseCaseImpl implements ArmazenarFotoProdutoUseCase {

    private final FotoProdutoGateway fotoProdutoGateway;
    private final ProdutoGateway produtoGateway;
    private final FotoArquivoStorageGateway fotoArquivoStorageGateway;

    public ArmazenarFotoProdutoUseCaseImpl(
            FotoProdutoGateway fotoProdutoGateway,
            ProdutoGateway produtoGateway,
            FotoArquivoStorageGateway fotoArquivoStorageGateway
    ) {
        this.fotoProdutoGateway = fotoProdutoGateway;
        this.produtoGateway = produtoGateway;
        this.fotoArquivoStorageGateway = fotoArquivoStorageGateway;
    }

    @Override
    public CompletableFuture<FotoProduto> store(Integer produtoId, MultipartFile file) throws IOException {
        Produto produto = produtoGateway.findById(produtoId).orElse(null);
        if (produto == null) throw new ProdutoNaoEncontradoException("Produto não encontrado");

        String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();
        byte[] conteudo = file.getBytes();

        return fotoArquivoStorageGateway.salvar(produtoId, nomeArquivo, conteudo)
                .thenApply(fotoArquivo -> {
                    FotoProduto fotoProduto = new FotoProduto();
                    fotoProduto.setFoto(fotoArquivo.getCaminhoRelativo());
                    fotoProduto.setProduto(produto);
                    fotoProduto.setCreatedAt(LocalDateTime.now());
                    return fotoProdutoGateway.save(fotoProduto);
                });
    }

    @Override
    @Transactional
    public CompletableFuture<Void> deletarFoto(Integer fotoId) {

        FotoProduto foto = fotoProdutoGateway.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        String caminhoRelativo = foto.getFoto();

        fotoProdutoGateway.delete(foto);

        fotoArquivoStorageGateway.deletar(caminhoRelativo);

        return CompletableFuture.completedFuture(null);
    }
}