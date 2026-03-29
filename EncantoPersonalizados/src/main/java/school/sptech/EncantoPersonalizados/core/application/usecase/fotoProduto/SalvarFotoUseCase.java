package school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto;

import school.sptech.EncantoPersonalizados.core.domain.FotoArquivo;

import java.util.concurrent.CompletableFuture;

public interface SalvarFotoUseCase {

    CompletableFuture<FotoArquivo> executar(Integer produtoId, String nomeArquivo, byte[] conteudo);
}
