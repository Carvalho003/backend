package school.sptech.EncantoPersonalizados.core.application.usecase.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.infrastructure.config.RabbitPropertiesConfiguration;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.messageDto;

@Service
public class ProducerUseCase {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitPropertiesConfiguration properties;

    public ProducerUseCase(RabbitTemplate rabbitTemplate, RabbitPropertiesConfiguration properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void send(messageDto message) {
        String exchangeName = properties.exchange().name();
        String routingKey = properties.queue().name();
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    public void sendReminderSent(messageDto message) {
        String exchangeName = properties.exchange().name();
        String routingKey = properties.reminders().routingKey();
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
