package school.sptech.EncantoPersonalizados.core.application.usecase.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.messageDto;

@Service
public class ConsumerUseCase {
    @RabbitListener(queues = "${broker.queue.name}")
    public void receive(messageDto message) {
        System.out.println("Received message: " + message.content());
    }
}
