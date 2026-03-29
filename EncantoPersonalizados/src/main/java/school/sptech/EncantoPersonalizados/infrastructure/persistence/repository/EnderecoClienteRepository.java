package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.EncantoPersonalizados.core.domain.EnderecoCliente;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Integer> {
}
