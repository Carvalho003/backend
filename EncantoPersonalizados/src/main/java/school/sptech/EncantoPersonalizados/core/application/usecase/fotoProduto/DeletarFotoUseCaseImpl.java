package school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto;

import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoArquivoStorageGateway;

import java.util.concurrent.CompletableFuture;

@Component
public class DeletarFotoUseCaseImpl implements DeletarFotoUseCase {

    private final FotoArquivoStorageGateway gateway;

    public DeletarFotoUseCaseImpl(FotoArquivoStorageGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CompletableFuture<Void> executar(String caminhoRelativo) {
        return gateway.deletar(caminhoRelativo);
    }
}
