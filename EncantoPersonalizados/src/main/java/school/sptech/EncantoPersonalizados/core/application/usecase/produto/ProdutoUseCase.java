package school.sptech.EncantoPersonalizados.core.application.usecase.produto;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoResponseDTO;

public interface ProdutoUseCase {
    Page<Produto> get(String search, String categoria, String tema, String item, boolean ativo, int page);
    Produto findById(Integer id);
    void mudarEstado(Integer id);
    Produto store(Produto entity);
    ProdutoResponseDTO storeFull(ProdutoRequestDTO dto);
    ProdutoResponseDTO update(ProdutoRequestDTO dto, Integer id);
}
