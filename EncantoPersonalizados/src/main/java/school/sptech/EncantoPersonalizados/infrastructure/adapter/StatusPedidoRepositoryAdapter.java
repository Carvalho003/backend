package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.StatusPedidoRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StatusPedidoRepositoryAdapter implements StatusPedidoGateway {

    private static final String CACHE_LIST = "statusPedidoList";

    private final StatusPedidoRepository repository;
    private final CacheManager cacheManager;

    public StatusPedidoRepositoryAdapter(StatusPedidoRepository repository, CacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "statusPedidoById", key = "#statusPedido.id", condition = "#statusPedido.id != null"),
            @CacheEvict(cacheNames = "statusPedidoFirstKanban", allEntries = true),
            @CacheEvict(cacheNames = CACHE_LIST, allEntries = true)
    })
    public StatusPedido save(StatusPedido statusPedido) {
        return repository.save(statusPedido);
    }

    @Override
    @Cacheable(cacheNames = "statusPedidoById", key = "#id", unless = "#result == null")
    public Optional<StatusPedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<StatusPedido> filtrar(Boolean ativo, Pageable pageable) {
        String key = cacheKey(ativo, pageable);
        Cache cache = cacheManager.getCache(CACHE_LIST);
        if (cache != null) {
            CachedListaStatusPedido hit = cache.get(key, CachedListaStatusPedido.class);
            if (hit != null) {
                return new PageImpl<>(hit.getContent(), pageable, hit.getTotalElements());
            }
        }
        Page<StatusPedido> result = repository.filtrar(ativo, pageable);
        if (cache != null) {
            cache.put(key, new CachedListaStatusPedido(result.getContent(), result.getTotalElements()));
        }
        return result;
    }

    private String cacheKey(Boolean ativo, Pageable pageable) {
        return Objects.hash(ativo, pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort().toString()) + "";
    }

    public static class CachedListaStatusPedido implements Serializable {
        private List<StatusPedido> content;
        private long totalElements;

        public CachedListaStatusPedido() {}

        public CachedListaStatusPedido(List<StatusPedido> content, long totalElements) {
            this.content = content;
            this.totalElements = totalElements;
        }

        public List<StatusPedido> getContent() { return content; }
        public void setContent(List<StatusPedido> content) { this.content = content; }
        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
    }

    @Override
    @Cacheable(cacheNames = "statusPedidoFirstKanban", key = "'first'", unless = "#result == null")
    public Optional<StatusPedido> findFirstByOrderByOrdemKanbanAsc() {
        return repository.findFirstByOrderByOrdemKanbanAsc();
    }

    @Override
    public List<StatusPedido> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByStatusIgnoreCaseAndAtivoTrue(String status) {
        return repository.existsByStatusIgnoreCaseAndAtivoTrue(status);
    }

    @Override
    public boolean existePedidoVinculado(Integer idStatus) {
        return repository.existePedidoVinculado(idStatus);
    }
}
