package school.sptech.EncantoPersonalizados.core.application.usecase.temaProduto;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoRequestDTO;

public interface TemaProdutoUseCase {
    TemaProduto findById(Integer id);
    Page<TemaProduto> get(String search, String categoria, int page, boolean ativo);
    TemaProduto store(TemaProdutoRequestDTO dto);
    TemaProduto update(TemaProdutoRequestDTO dto, Integer id);
    void mudarEstado(Integer id);
}
