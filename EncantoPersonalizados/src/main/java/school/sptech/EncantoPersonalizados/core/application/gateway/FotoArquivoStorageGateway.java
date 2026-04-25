package school.sptech.EncantoPersonalizados.core.application.gateway;

import school.sptech.EncantoPersonalizados.core.domain.FotoArquivo;
import java.util.concurrent.CompletableFuture;

public interface FotoArquivoStorageGateway {

    CompletableFuture<FotoArquivo> salvar(Integer produtoId, String nomeArquivo, byte[] conteudo);

    CompletableFuture<String> salvarFotoUsuario(Integer usuarioId, String nomeArquivo, byte[] conteudo);

    CompletableFuture<Void> deletar(String caminhoRelativo);
}