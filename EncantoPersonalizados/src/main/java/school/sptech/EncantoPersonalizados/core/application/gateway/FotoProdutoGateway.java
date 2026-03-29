package school.sptech.EncantoPersonalizados.core.application.gateway;

import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;

import java.util.Optional;

public interface FotoProdutoGateway {
    FotoProduto save(FotoProduto foto);
    Optional<FotoProduto> findById(Integer id);
    void delete(FotoProduto foto);
}
