package school.sptech.EncantoPersonalizados.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker")
public record RabbitPropertiesConfiguration(
        Exchange exchange,
        Queue queue,
    Reminders reminders,
    FotoProdutoEvents fotoProdutoEvents) {
    public record Exchange(String name) {
    }

    public record Queue(String name) {
    }

    public record Reminders(String queueName, String routingKey) {
    }

    public record FotoProdutoEvents(String exchangeName, String queueName) {
    }
}
