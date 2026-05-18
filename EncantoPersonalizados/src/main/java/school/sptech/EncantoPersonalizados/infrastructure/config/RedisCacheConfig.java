package school.sptech.EncantoPersonalizados.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory cf) {

        // ObjectMapper dedicado ao cache: NÃO reutilizamos o bean global do Spring MVC
        // porque GenericJackson2JsonRedisSerializer ativa default typing, e ativar isso
        // no ObjectMapper global quebraria toda serialização HTTP do projeto.
        ObjectMapper cacheMapper = buildCacheObjectMapper();

        var json = new GenericJackson2JsonRedisSerializer(cacheMapper);

        // Configuração padrão para todos os caches
        var defaults = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json))
                .disableCachingNullValues()
                .prefixCacheNameWith("app::")
                .entryTtl(Duration.ofMinutes(5));

        // Configurações específicas por cache (TTL próprio)
        var perCache = Map.ofEntries(
                Map.entry("fotoProdutoById", defaults.entryTtl(Duration.ofMinutes(10))),
                Map.entry("statusPedidoById", defaults.entryTtl(Duration.ofMinutes(10))),
                Map.entry("statusPedidoFirstKanban", defaults.entryTtl(Duration.ofMinutes(10))),
                Map.entry("statusPedidoList", defaults.entryTtl(Duration.ofMinutes(5)))
        );

        return RedisCacheManager.builder(cf)
                .cacheDefaults(defaults)
                .withInitialCacheConfigurations(perCache)
                .build();
    }

    // package-private para permitir testes de round-trip sem precisar de Redis real
    ObjectMapper buildCacheObjectMapper() {
        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module()); // Suporte a java.util.Optional no payload cacheado
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return mapper;
    }
}
