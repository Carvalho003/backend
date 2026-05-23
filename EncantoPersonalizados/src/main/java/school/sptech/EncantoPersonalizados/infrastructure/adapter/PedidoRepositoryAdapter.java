package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.PedidoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepositoryAdapter implements PedidoGateway {

    private final PedidoRepository repository;
    private final EntityManager entityManager;

    public PedidoRepositoryAdapter(PedidoRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Pedido> findByIdWithDetails(Integer id) {
        return repository.findByIdWithDetails(id);
    }

    @Override
    public Page<Pedido> filtrar(String search, Boolean ativo, LocalDate inicio, LocalDate fim, Pageable pageable, Integer size) {
        return repository.filtrar(
                search,
                ativo,
                inicio != null ? inicio.atStartOfDay() : null,
                fim != null ? fim.atTime(23, 59, 59) : null,
                pageable
        );
    }

    @Override
    public Page<Pedido> filtrarAvancado(
            String search,
            Boolean ativo,
            String origem,
            Integer statusId,
            LocalDate createdAtInicio,
            LocalDate createdAtFim,
            LocalDate dataLimiteInicio,
            LocalDate dataLimiteFim,
            Double valorMin,
            Double valorMax,
            String sortBy,
            String sortDirection,
            Pageable pageable
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> query = cb.createQuery(Pedido.class);
        Root<Pedido> root = query.from(Pedido.class);
        Join<Pedido, ?> cliente = root.join("cliente", JoinType.LEFT);
        Join<Pedido, PedidoStatusPedido> pedidoStatus = root.join("pedidoStatusPedidos", JoinType.LEFT);
        Join<PedidoStatusPedido, StatusPedido> status = pedidoStatus.join("status", JoinType.LEFT);

        query.select(root).distinct(true);
        query.where(buildPredicates(
                cb, root, cliente, pedidoStatus, status, search, ativo, origem, statusId,
                createdAtInicio, createdAtFim, dataLimiteInicio, dataLimiteFim, valorMin, valorMax
        ).toArray(Predicate[]::new));

        Order order = buildOrder(cb, root, status, sortBy, sortDirection);
        if (order != null) {
            query.orderBy(order);
        }

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Pedido> pedidos = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Pedido> countRoot = countQuery.from(Pedido.class);
        Join<Pedido, ?> countCliente = countRoot.join("cliente", JoinType.LEFT);
        Join<Pedido, PedidoStatusPedido> countPedidoStatus = countRoot.join("pedidoStatusPedidos", JoinType.LEFT);
        Join<PedidoStatusPedido, StatusPedido> countStatus = countPedidoStatus.join("status", JoinType.LEFT);

        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(buildPredicates(
                cb, countRoot, countCliente, countPedidoStatus, countStatus, search, ativo, origem, statusId,
                createdAtInicio, createdAtFim, dataLimiteInicio, dataLimiteFim, valorMin, valorMax
        ).toArray(Predicate[]::new));

        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(pedidos, pageable, total);
    }

    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<Pedido> root,
            Join<Pedido, ?> cliente,
            Join<Pedido, PedidoStatusPedido> pedidoStatus,
            Join<PedidoStatusPedido, StatusPedido> status,
            String search,
            Boolean ativo,
            String origem,
            Integer statusId,
            LocalDate createdAtInicio,
            LocalDate createdAtFim,
            LocalDate dataLimiteInicio,
            LocalDate dataLimiteFim,
            Double valorMin,
            Double valorMax
    ) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("ativo"), ativo));
        predicates.add(cb.isTrue(pedidoStatus.get("statusAtual")));

        String searchNormalizado = normalizarTexto(search);
        if (searchNormalizado != null) {
            predicates.add(cb.or(
                    cb.like(cb.lower(cliente.get("nome")), "%" + searchNormalizado + "%"),
                    cb.like(cb.lower(root.get("origem")), "%" + searchNormalizado + "%")
            ));
        }

        String origemNormalizada = normalizarTexto(origem);
        if (origemNormalizada != null) {
            predicates.add(cb.like(cb.lower(root.get("origem")), "%" + origemNormalizada + "%"));
        }

        if (statusId != null) {
            predicates.add(cb.equal(status.get("id"), statusId));
        }

        if (createdAtInicio != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAtInicio.atStartOfDay()));
        }

        if (createdAtFim != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdAtFim.atTime(23, 59, 59)));
        }

        if (dataLimiteInicio != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dataLimite"), dataLimiteInicio.atStartOfDay()));
        }

        if (dataLimiteFim != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dataLimite"), dataLimiteFim.atTime(23, 59, 59)));
        }

        if (valorMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("precoTotal"), valorMin));
        }

        if (valorMax != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("precoTotal"), valorMax));
        }

        return predicates;
    }

    private Order buildOrder(CriteriaBuilder cb, Root<Pedido> root, Join<PedidoStatusPedido, StatusPedido> status, String sortBy, String sortDirection) {
        String campoOrdenacao = normalizarTexto(sortBy);
        if (campoOrdenacao == null) return null;

        boolean desc = "desc".equalsIgnoreCase(sortDirection);

        return switch (campoOrdenacao) {
            case "origem" -> desc ? cb.desc(root.get("origem")) : cb.asc(root.get("origem"));
            case "status" -> desc ? cb.desc(status.get("status")) : cb.asc(status.get("status"));
            case "valor" -> desc ? cb.desc(root.get("precoTotal")) : cb.asc(root.get("precoTotal"));
            case "createdat", "criacao" -> desc ? cb.desc(root.get("createdAt")) : cb.asc(root.get("createdAt"));
            case "datalimite", "entrega" -> desc ? cb.desc(root.get("dataLimite")) : cb.asc(root.get("dataLimite"));
            default -> null;
        };
    }

    private String normalizarTexto(String valor) {
        if (valor == null || valor.trim().isEmpty()) return null;
        return valor.trim().toLowerCase();
    }
}
