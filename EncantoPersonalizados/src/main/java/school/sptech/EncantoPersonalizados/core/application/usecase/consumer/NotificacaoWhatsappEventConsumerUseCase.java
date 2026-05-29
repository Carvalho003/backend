package school.sptech.EncantoPersonalizados.core.application.usecase.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.core.application.usecase.whatsapp.WhatsappUseCase;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.NotificacaoWhatsappEventMessageDto;

@Service
public class NotificacaoWhatsappEventConsumerUseCase {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoWhatsappEventConsumerUseCase.class);
    private final WhatsappUseCase whatsappUseCase;

    // Aqui injetamos o seu WhatsappUseCase original
    public NotificacaoWhatsappEventConsumerUseCase(WhatsappUseCase whatsappUseCase) {
        this.whatsappUseCase = whatsappUseCase;
    }

    // Esta anotação faz o Spring ficar escutando a fila.
    // O nome da fila deve bater com o routingKey/queue que você usou no Producer.
    @RabbitListener(queues = "whatsapp.queue")
    public void consumirMensagemWhatsapp(NotificacaoWhatsappEventMessageDto mensagem) {
        log.info("Processando notificação de WhatsApp em background para o pedido: {}", mensagem.idPedido());

        try {
            // AQUI NÓS CHAMAMOS A SUA LÓGICA ORIGINAL DO WHATSAPP!
            // Note que o seu WhatsappUseCase original já tem o método enviarMensagemQuandoPedidoConcluido()
            // que manda pro número fixo que você configurou.
            boolean enviado = whatsappUseCase.enviarMensagemQuandoPedidoConcluido();

            if (enviado) {
                log.info("Notificação de conclusão do pedido {} enviada com sucesso!", mensagem.idPedido());
            } else {
                log.warn("Falha ao enviar notificação do pedido {}. A API pode estar offline.", mensagem.idPedido());
            }

        } catch (Exception e) {
            // Se der erro no GoWa, cai aqui no back-end.
            // Como isso roda em background, o usuário no Front-end (Kanban) não vê erro nenhum!
            log.error("Erro ao processar envio de WhatsApp na fila. Detalhes: {}", e.getMessage());
        }
    }
}