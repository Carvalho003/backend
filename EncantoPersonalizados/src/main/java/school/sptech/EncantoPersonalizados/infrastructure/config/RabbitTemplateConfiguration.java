package school.sptech.EncantoPersonalizados.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
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
public FanoutExchange exchange() {
    return new FanoutExchange(properties.exchange().name());
}

@Bean
public Queue queue() {
    return new Queue(properties.queue().name(), true);
}

@Bean
public Binding binding(Queue queue, FanoutExchange exchange) {
    return BindingBuilder
            .bind(queue)
            .to(exchange);
}
@Bean
public MessageConverter messageConverter() {
return new SimpleMessageConverter();
}
}
