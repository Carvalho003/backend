package school.sptech.EncantoPersonalizados.core.application.gateway;

import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

import java.util.Optional;

public interface ProdutoPedidoGateway {
    ProdutoPedido save(ProdutoPedido entity);
    Optional<ProdutoPedido> findById(Integer id);
    void deleteById(Integer id);
}
