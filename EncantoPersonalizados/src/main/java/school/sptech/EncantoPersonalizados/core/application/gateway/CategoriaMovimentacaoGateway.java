package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;

import java.util.Optional;

public interface CategoriaMovimentacaoGateway {
    CategoriaMovimentacao save(CategoriaMovimentacao categoria);
    Optional<CategoriaMovimentacao> findById(Integer id);
    Optional<CategoriaMovimentacao> findByIdAndStatusTrue(Integer id);
    Page<CategoriaMovimentacao> filtrar(String search, Boolean status, Pageable pageable);
    void deleteById(Integer id);
    boolean existsByDescricao(String descricao);
}
