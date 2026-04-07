package school.sptech.EncantoPersonalizados.infrastructure.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.infrastructure.config.RabbitPropertiesConfiguration;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.messageDto;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitPropertiesConfiguration properties;

    public ProducerService(RabbitTemplate rabbitTemplate, RabbitPropertiesConfiguration properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void send(messageDto message) {
        String exchangeName = properties.exchange().name();
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
