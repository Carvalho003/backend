package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;

public interface FotoProdutoRepository extends JpaRepository<FotoProduto, Integer> {
}
