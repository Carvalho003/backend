package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.StatusPedidoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
@SpringJUnitConfig(StatusPedidoRepositoryAdapterCacheTest.Config.class)
class StatusPedidoRepositoryAdapterCacheTest {

    @EnableCaching
    @Configuration
    static class Config {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(
                    "statusPedidoById",
                    "statusPedidoFirstKanban",
                    "statusPedidoList"
            );
        }

        @Bean
        StatusPedidoRepository statusPedidoRepository() {
            return mock(StatusPedidoRepository.class);
        }

        @Bean
        StatusPedidoRepositoryAdapter statusPedidoRepositoryAdapter(StatusPedidoRepository repo, CacheManager cm) {
            return new StatusPedidoRepositoryAdapter(repo, cm);
        }
    }

    @Autowired
    StatusPedidoGateway adapter;

    @Autowired
    StatusPedidoRepository repository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void clearCaches() {
        reset(repository);
        cacheManager.getCacheNames().forEach(name -> {
            var c = cacheManager.getCache(name);
            if (c != null) c.clear();
        });
    }

    private StatusPedido buildStatus(Integer id, String nome) {
        StatusPedido sp = new StatusPedido();
        sp.setId(id);
        sp.setStatus(nome);
        sp.setOrdemKanban(1);
        sp.setAtivo(true);
        return sp;
    }

    @Test
    @DisplayName("findById: segunda chamada vem do cache (repository chamado uma única vez)")
    void findByIdUsaCache() {
        StatusPedido sp = buildStatus(1, "Em produção");
        when(repository.findById(1)).thenReturn(Optional.of(sp));

        Optional<StatusPedido> r1 = adapter.findById(1);
        Optional<StatusPedido> r2 = adapter.findById(1);

        assertTrue(r1.isPresent());
        assertTrue(r2.isPresent());
        verify(repository, times(1)).findById(1);
        assertNotNull(cacheManager.getCache("statusPedidoById").get(1));
    }

    @Test
    @DisplayName("findById: Optional vazio NÃO é cacheado")
    void findByIdNaoCacheiaVazio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        adapter.findById(99);
        adapter.findById(99);

        verify(repository, times(2)).findById(99);
        assertNull(cacheManager.getCache("statusPedidoById").get(99));
    }

    @Test
    @DisplayName("findFirstByOrderByOrdemKanbanAsc: segunda chamada vem do cache")
    void findFirstUsaCache() {
        StatusPedido sp = buildStatus(2, "Aguardando");
        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(sp));

        adapter.findFirstByOrderByOrdemKanbanAsc();
        adapter.findFirstByOrderByOrdemKanbanAsc();

        verify(repository, times(1)).findFirstByOrderByOrdemKanbanAsc();
        assertNotNull(cacheManager.getCache("statusPedidoFirstKanban").get("first"));
    }

    @Test
    @DisplayName("filtrar: cacheia por (ativo, page, size, sort)")
    void filtrarUsaCache() {
        Pageable pg = PageRequest.of(0, 10);
        Page<StatusPedido> page = new PageImpl<>(List.of(buildStatus(1, "A"), buildStatus(2, "B")), pg, 2);
        when(repository.filtrar(eq(true), any(Pageable.class))).thenReturn(page);

        adapter.filtrar(true, pg);
        adapter.filtrar(true, pg);

        verify(repository, times(1)).filtrar(eq(true), any(Pageable.class));

        // Mesmo ativo, página diferente → novo hit no repositório
        Pageable pg2 = PageRequest.of(1, 10);
        when(repository.filtrar(eq(true), eq(pg2))).thenReturn(new PageImpl<>(List.of(), pg2, 0));
        adapter.filtrar(true, pg2);
        verify(repository, times(1)).filtrar(eq(true), eq(pg2));
    }

    @Test
    @DisplayName("save invalida statusPedidoById (pela id), statusPedidoFirstKanban e statusPedidoList (allEntries)")
    void saveInvalidaCaches() {
        StatusPedido sp = buildStatus(5, "Em revisão");
        when(repository.findById(5)).thenReturn(Optional.of(sp));
        when(repository.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(sp));
        Pageable pg = PageRequest.of(0, 10);
        when(repository.filtrar(eq(true), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(sp), pg, 1));

        // Popula os 3 caches
        adapter.findById(5);
        adapter.findFirstByOrderByOrdemKanbanAsc();
        adapter.filtrar(true, pg);
        assertNotNull(cacheManager.getCache("statusPedidoById").get(5));
        assertNotNull(cacheManager.getCache("statusPedidoFirstKanban").get("first"));

        // save → invalida tudo
        when(repository.save(sp)).thenReturn(sp);
        adapter.save(sp);

        assertNull(cacheManager.getCache("statusPedidoById").get(5));
        assertNull(cacheManager.getCache("statusPedidoFirstKanban").get("first"));
        // Próxima chamada deve ir de novo ao repositório
        adapter.findById(5);
        verify(repository, times(2)).findById(5);
    }

    @Test
    @DisplayName("save com id nulo não tenta evictar key nula em statusPedidoById")
    void saveSemIdNaoFalha() {
        StatusPedido novo = new StatusPedido();
        novo.setStatus("Novo status");
        StatusPedido salvo = buildStatus(10, "Novo status");
        when(repository.save(novo)).thenReturn(salvo);

        assertDoesNotThrow(() -> adapter.save(novo));
        verify(repository, times(1)).save(novo);
    }
}
