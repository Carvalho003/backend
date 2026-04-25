package school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ;

import java.time.LocalDateTime;

public record FotoProdutoEventMessageDto(
        String eventType,
        Integer produtoId,
        Integer fotoId,
        String caminhoFoto,
        LocalDateTime occurredAt,
        String origem
) {
    public static FotoProdutoEventMessageDto fotoCriada(Integer produtoId, Integer fotoId, String caminhoFoto) {
        return new FotoProdutoEventMessageDto(
                "FOTO_CRIADA",
                produtoId,
                fotoId,
                caminhoFoto,
                LocalDateTime.now(),
                "encanto-backend"
        );
    }

    public static FotoProdutoEventMessageDto fotoRemovida(Integer produtoId, Integer fotoId, String caminhoFoto) {
        return new FotoProdutoEventMessageDto(
                "FOTO_REMOVIDA",
                produtoId,
                fotoId,
                caminhoFoto,
                LocalDateTime.now(),
                "encanto-backend"
        );
    }
}
