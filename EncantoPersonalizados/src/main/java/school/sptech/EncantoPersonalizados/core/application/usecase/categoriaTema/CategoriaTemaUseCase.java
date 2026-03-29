package school.sptech.EncantoPersonalizados.core.application.usecase.categoriaTema;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaResponseDTO;

public interface CategoriaTemaUseCase {
    CategoriaTemaResponseDTO criar(CategoriaTemaRequestDTO dto);
    CategoriaTemaResponseDTO update(CategoriaTemaRequestDTO dto, Integer id);
    Page<CategoriaTema> listar(String search, Boolean ativo, int page);
    CategoriaTema findById(Integer id);
    void mudarEstado(Integer id);
}
