package school.sptech.EncantoPersonalizados.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("encantoTopicExchange")
    public TopicExchange exchange() {
        return new TopicExchange(properties.exchange().name());
    }

    @Bean
    @Qualifier("encantoMainQueue")
    public Queue queue() {
        return new Queue(properties.queue().name(), true);
    }

    @Bean
    @Qualifier("encantoRemindersQueue")
    public Queue remindersQueue() {
        return new Queue(properties.reminders().queueName(), true);
    }

    @Bean
    @Qualifier("fotoProdutoEventsExchange")
    public FanoutExchange fotoProdutoEventsExchange() {
        return new FanoutExchange(properties.fotoProdutoEvents().exchangeName());
    }

    @Bean
    @Qualifier("fotoProdutoEventsQueue")
    public Queue fotoProdutoEventsQueue() {
        return new Queue(properties.fotoProdutoEvents().queueName(), true);
    }

    @Bean
    public Binding binding(
            @Qualifier("encantoMainQueue") Queue queue,
            @Qualifier("encantoTopicExchange") TopicExchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queue.getName());
    }

    @Bean
    public Binding remindersBinding(
            @Qualifier("encantoRemindersQueue") Queue remindersQueue,
            @Qualifier("encantoTopicExchange") TopicExchange exchange
    ) {
        return BindingBuilder
                .bind(remindersQueue)
                .to(exchange)
                .with(properties.reminders().routingKey());
    }

    @Bean
    public Binding fotoProdutoEventsBinding(
            @Qualifier("fotoProdutoEventsQueue") Queue fotoProdutoEventsQueue,
            @Qualifier("fotoProdutoEventsExchange") FanoutExchange fotoProdutoEventsExchange
    ) {
        return BindingBuilder
                .bind(fotoProdutoEventsQueue)
                .to(fotoProdutoEventsExchange);
    }
    // 1. Cria a fila no RabbitMQ automaticamente ao iniciar a aplicação
    @Bean
    @Qualifier("whatsappQueue")
    public Queue whatsappQueue() {
        // O "true" indica que a fila é durável (sobrevive a reinicializações do RabbitMQ)
        return new Queue("whatsapp.queue", true);
    }

    // 2. Conecta (Binding) a nova fila ao Exchange (roteador de mensagens) principal do projeto
    @Bean
    public Binding whatsappBinding(
            @Qualifier("whatsappQueue") Queue whatsappQueue,
            @Qualifier("encantoTopicExchange") TopicExchange exchange
    ) {
        return BindingBuilder
                .bind(whatsappQueue)
                .to(exchange)
                .with("whatsapp.queue");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
