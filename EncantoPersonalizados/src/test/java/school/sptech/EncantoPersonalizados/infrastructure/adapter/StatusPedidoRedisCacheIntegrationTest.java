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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.config.RedisCacheConfig;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.StatusPedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Teste funcional do cache do StatusPedido contra Redis real.
 * Foco particular: valida o round-trip de Page<StatusPedido>, que é o caminho de maior risco.
 */
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(StatusPedidoRedisCacheIntegrationTest.Config.class)
class StatusPedidoRedisCacheIntegrationTest {

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
        StatusPedidoRepository statusPedidoRepository() {
            return mock(StatusPedidoRepository.class);
        }

        @Bean
        StatusPedidoRepositoryAdapter statusPedidoRepositoryAdapter(StatusPedidoRepository r, CacheManager cm) {
            return new StatusPedidoRepositoryAdapter(r, cm);
        }
    }

    @Autowired
    StatusPedidoGateway gateway;

    @Autowired
    StatusPedidoRepository repository;

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

    private StatusPedido buildStatus(Integer id, String nome, Integer ordem) {
        StatusPedido sp = new StatusPedido();
        sp.setId(id);
        sp.setStatus(nome);
        sp.setCor("#FF00AA");
        sp.setOrdemKanban(ordem);
        sp.setAtivo(true);
        sp.setCreatedAt(LocalDateTime.of(2026, 5, 17, 9, 0, 0));
        sp.setUpdatedAt(LocalDateTime.of(2026, 5, 17, 10, 30, 0));
        return sp;
    }

    @Test
    @DisplayName("findById: round-trip via Redis preserva todos os campos do StatusPedido")
    void findByIdRoundTripPreservaCampos() {
        StatusPedido sp = buildStatus(11, "Em produção", 2);
        when(repository.findById(11)).thenReturn(Optional.of(sp));

        gateway.findById(11);
        StatusPedido recuperado = gateway.findById(11).orElseThrow();

        verify(repository, times(1)).findById(11);
        assertEquals(11, recuperado.getId());
        assertEquals("Em produção", recuperado.getStatus());
        assertEquals("#FF00AA", recuperado.getCor());
        assertEquals(2, recuperado.getOrdemKanban());
        assertTrue(recuperado.isAtivo());
        assertEquals(sp.getCreatedAt(), recuperado.getCreatedAt());
        assertEquals(sp.getUpdatedAt(), recuperado.getUpdatedAt());
        // @JsonIgnore na coleção pedidoStatusPedidos: vem nulo ou vazio
        assertTrue(recuperado.getPedidoStatusPedidos() == null
                || recuperado.getPedidoStatusPedidos().isEmpty());
    }

    @Test
    @DisplayName("findFirstByOrderByOrdemKanbanAsc usa cache com key fixa 'first'")
    void findFirstUsaCache() {
        StatusPedido sp = buildStatus(1, "Aguardando", 1);
        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(sp));

        gateway.findFirstByOrderByOrdemKanbanAsc();
        gateway.findFirstByOrderByOrdemKanbanAsc();

        verify(repository, times(1)).findFirstByOrderByOrdemKanbanAsc();
    }

    @Test
    @DisplayName("filtrar: Page<StatusPedido> faz round-trip via Redis (PageImpl preservada)")
    void filtrarPageRoundTrip() {
        Pageable pg = PageRequest.of(0, 10);
        List<StatusPedido> conteudo = List.of(
                buildStatus(1, "Aguardando", 1),
                buildStatus(2, "Em produção", 2)
        );
        Page<StatusPedido> page = new PageImpl<>(conteudo, pg, 2);
        when(repository.filtrar(eq(true), any(Pageable.class))).thenReturn(page);

        // Primeira chamada: serializa Page no Redis
        Page<StatusPedido> p1 = gateway.filtrar(true, pg);
        // Segunda chamada: desserializa do Redis
        Page<StatusPedido> p2 = gateway.filtrar(true, pg);

        verify(repository, times(1)).filtrar(eq(true), any(Pageable.class));
        assertEquals(2, p1.getTotalElements());
        assertEquals(2, p2.getTotalElements());
        assertEquals(2, p2.getContent().size());
        assertEquals("Aguardando", p2.getContent().get(0).getStatus());
        assertEquals("Em produção", p2.getContent().get(1).getStatus());
    }

    @Test
    @DisplayName("filtrar: paginação diferente gera entrada de cache separada")
    void filtrarPaginacaoDiferenteCriaEntradaSeparada() {
        Pageable pg0 = PageRequest.of(0, 10);
        Pageable pg1 = PageRequest.of(1, 10);
        when(repository.filtrar(eq(true), eq(pg0)))
                .thenReturn(new PageImpl<>(List.of(buildStatus(1, "A", 1)), pg0, 1));
        when(repository.filtrar(eq(true), eq(pg1)))
                .thenReturn(new PageImpl<>(List.of(), pg1, 0));

        gateway.filtrar(true, pg0);
        gateway.filtrar(true, pg0);
        gateway.filtrar(true, pg1);

        verify(repository, times(1)).filtrar(eq(true), eq(pg0));
        verify(repository, times(1)).filtrar(eq(true), eq(pg1));
    }

    @Test
    @DisplayName("save invalida os 3 caches (statusPedidoById, statusPedidoFirstKanban, statusPedidoList)")
    void saveInvalidaTodosOsCaches() {
        StatusPedido sp = buildStatus(7, "Em revisão", 3);
        Pageable pg = PageRequest.of(0, 10);

        when(repository.findById(7)).thenReturn(Optional.of(sp));
        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(sp));
        when(repository.filtrar(eq(true), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sp), pg, 1));
        when(repository.save(sp)).thenReturn(sp);

        // Popula os 3 caches
        gateway.findById(7);
        gateway.findFirstByOrderByOrdemKanbanAsc();
        gateway.filtrar(true, pg);

        // save invalida tudo
        gateway.save(sp);

        // Próximas chamadas precisam ir de novo ao repositório
        gateway.findById(7);
        gateway.findFirstByOrderByOrdemKanbanAsc();
        gateway.filtrar(true, pg);

        verify(repository, times(2)).findById(7);
        verify(repository, times(2)).findFirstByOrderByOrdemKanbanAsc();
        verify(repository, times(2)).filtrar(eq(true), any(Pageable.class));
    }
}
