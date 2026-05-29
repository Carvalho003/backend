package school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ;

import java.time.LocalDateTime;

public record NotificacaoWhatsappEventMessageDto(
        Integer idPedido,
        String nomeCliente,
        String telefoneCliente,
        String novoStatus,
        LocalDateTime occurredAt
) {
}