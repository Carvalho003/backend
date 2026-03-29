package school.sptech.EncantoPersonalizados.core.application.usecase.categoriaMovimentacao;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;

public interface CategoriaMovimentacaoUseCase {
    ResponseCategoriaMovimentacaoDTO create(RequestCategoriaMovimentacaoDTO dto);
    Page<CategoriaMovimentacao> get(String search, Boolean status, int page);
    ResponseCategoriaMovimentacaoDTO findById(Integer id);
    ResponseCategoriaMovimentacaoDTO update(Integer id, RequestCategoriaMovimentacaoDTO dto);
    void delete(Integer id);
}
