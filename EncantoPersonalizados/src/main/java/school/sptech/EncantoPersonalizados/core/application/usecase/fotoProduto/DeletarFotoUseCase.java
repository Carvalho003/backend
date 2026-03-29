package school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto;

import java.util.concurrent.CompletableFuture;

public interface DeletarFotoUseCase {

    CompletableFuture<Void> executar(String caminhoRelativo);
}
