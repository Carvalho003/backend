package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer> {

}
