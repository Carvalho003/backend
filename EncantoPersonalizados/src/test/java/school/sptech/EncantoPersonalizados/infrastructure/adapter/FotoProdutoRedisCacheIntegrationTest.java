package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.infrastructure.config.RedisCacheConfig;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.FotoProdutoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste funcional contra uma instância real do Redis em container.
 * Valida o round-trip completo (serialização JSON real, default typing, JavaTimeModule, Jdk8Module)
 * e a integração entre Spring Cache, Lettuce e o RedisCacheConfig do projeto.
 */
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(FotoProdutoRedisCacheIntegrationTest.Config.class)
class FotoProdutoRedisCacheIntegrationTest {

    static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @BeforeAll
    static void startRedis() {
        REDIS.start();
    }

    @AfterAll
    static void stopRedis() {
        REDIS.stop();
    }

    @EnableCaching
    @Configuration
    @Import(RedisCacheConfig.class)
    static class Config {
        @Bean
        RedisConnectionFactory redisConnectionFactory() {
            RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration(
                    REDIS.getHost(), REDIS.getMappedPort(6379));
            return new LettuceConnectionFactory(cfg);
        }

        @Bean
        FotoProdutoRepository fotoProdutoRepository() {
            return mock(FotoProdutoRepository.class);
        }

        @Bean
        FotoProdutoRepositoryAdapter fotoProdutoRepositoryAdapter(FotoProdutoRepository r) {
            return new FotoProdutoRepositoryAdapter(r);
        }
    }

    @Autowired
    FotoProdutoGateway gateway;

    @Autowired
    FotoProdutoRepository repository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void clear() {
        reset(repository);
        cacheManager.getCacheNames().forEach(name -> {
            var c = cacheManager.getCache(name);
            if (c != null) c.clear();
        });
    }

    private FotoProduto buildFoto(Integer id) {
        FotoProduto f = new FotoProduto();
        f.setId(id);
        f.setFoto("/uploads/produtos/9/uuid-" + id + ".jpg");
        f.setCreatedAt(LocalDateTime.of(2026, 5, 17, 12, 0, 0));
        f.setUpdatedAt(LocalDateTime.of(2026, 5, 17, 13, 30, 0));
        Produto p = new Produto();
        p.setId(9);
        f.setProduto(p);
        return f;
    }

    @Test
    @DisplayName("findById: segunda chamada vem do Redis real (repository chamado uma vez)")
    void findByIdHitsCacheOnSecondCall() {
        FotoProduto f = buildFoto(101);
        when(repository.findById(101)).thenReturn(Optional.of(f));

        Optional<FotoProduto> r1 = gateway.findById(101);
        Optional<FotoProduto> r2 = gateway.findById(101);

        assertTrue(r1.isPresent());
        assertTrue(r2.isPresent());
        verify(repository, times(1)).findById(101);
    }

    @Test
    @DisplayName("Round-trip via Redis preserva id, foto e datas; produto vem nulo (@JsonIgnore)")
    void redisRoundTripPreservaCampos() {
        FotoProduto f = buildFoto(202);
        when(repository.findById(202)).thenReturn(Optional.of(f));

        // Primeira chamada popula o cache no Redis (via serializer real)
        gateway.findById(202);
        // Segunda chamada faz a desserialização — é o que importa
        FotoProduto recuperado = gateway.findById(202).orElseThrow();

        assertEquals(202, recuperado.getId());
        assertEquals(f.getFoto(), recuperado.getFoto());
        assertEquals(f.getCreatedAt(), recuperado.getCreatedAt());
        assertEquals(f.getUpdatedAt(), recuperado.getUpdatedAt());
        // @JsonIgnore — Produto não é serializado
        assertNull(recuperado.getProduto());
    }

    @Test
    @DisplayName("Optional vazio NÃO entra no Redis")
    void findByIdNaoCacheiaVazio() {
        when(repository.findById(404)).thenReturn(Optional.empty());

        gateway.findById(404);
        gateway.findById(404);

        verify(repository, times(2)).findById(404);
    }

    @Test
    @DisplayName("save invalida a entrada no Redis (próxima leitura vai ao repositório)")
    void saveInvalidaCache() {
        FotoProduto f = buildFoto(303);
        when(repository.findById(303)).thenReturn(Optional.of(f));
        when(repository.save(any(FotoProduto.class))).thenReturn(f);

        // Popula o cache
        gateway.findById(303);
        verify(repository, times(1)).findById(303);

        // Save invalida
        gateway.save(f);

        // Próxima leitura precisa ir ao repositório novamente
        gateway.findById(303);
        verify(repository, times(2)).findById(303);
    }

    @Test
    @DisplayName("delete usa deleteById e invalida a entrada no Redis")
    void deleteInvalidaCache() {
        FotoProduto f = buildFoto(505);
        when(repository.findById(505)).thenReturn(Optional.of(f));

        gateway.findById(505);
        verify(repository, times(1)).findById(505);

        gateway.delete(f);
        verify(repository, times(1)).deleteById(505);
        verify(repository, never()).delete(any(FotoProduto.class));

        gateway.findById(505);
        verify(repository, times(2)).findById(505);
    }

    @Test
    @DisplayName("findByIdUncached não consulta nem popula o cache do Redis")
    void findByIdUncachedIgnoraCache() {
        FotoProduto f = buildFoto(606);
        when(repository.findById(606)).thenReturn(Optional.of(f));

        gateway.findByIdUncached(606);
        gateway.findByIdUncached(606);

        // Cada chamada vai direto ao repositório
        verify(repository, times(2)).findById(606);

        // E não populou o cache: findById agora também precisa ir ao repositório
        gateway.findById(606);
        verify(repository, times(3)).findById(606);
    }
}
