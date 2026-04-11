package school.sptech.EncantoPersonalizados.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitPropertiesConfiguration.class)
public class RabbitTemplateConfiguration {
    private final RabbitPropertiesConfiguration properties;

    public RabbitTemplateConfiguration(RabbitPropertiesConfiguration properties) {
        this.properties = properties;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(properties.exchange().name());
    }

    @Bean
    public Queue queue() {
        return new Queue(properties.queue().name(), true);
    }

    @Bean
    public Queue remindersQueue() {
        return new Queue(properties.reminders().queueName(), true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queue.getName());
    }

    @Bean
    public Binding remindersBinding(Queue remindersQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(remindersQueue)
                .to(exchange)
                .with(properties.reminders().routingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
