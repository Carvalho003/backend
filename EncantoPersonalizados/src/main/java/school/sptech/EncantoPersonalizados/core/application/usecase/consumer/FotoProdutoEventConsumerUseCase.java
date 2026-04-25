package school.sptech.EncantoPersonalizados.core.application.usecase.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.FotoProdutoEventMessageDto;

@Service
public class FotoProdutoEventConsumerUseCase {

    private static final Logger log = LoggerFactory.getLogger(FotoProdutoEventConsumerUseCase.class);

    @RabbitListener(queues = "${broker.foto-produto-events.queue-name}")
    public void receive(FotoProdutoEventMessageDto event) {
        log.info(
                "Evento foto produto recebido: tipo={}, produtoId={}, fotoId={}, caminho={}",
                event.eventType(),
                event.produtoId(),
                event.fotoId(),
                event.caminhoFoto()
        );
    }
}
