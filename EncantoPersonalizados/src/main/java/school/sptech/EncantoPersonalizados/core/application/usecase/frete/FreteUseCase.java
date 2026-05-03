package school.sptech.EncantoPersonalizados.core.application.usecase.frete;

import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.CalcularFreteRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.FreteResponseDTO;

import java.util.List;

public interface FreteUseCase {
    List<FreteResponseDTO> calcularFrete(CalcularFreteRequestDTO requestDto);
}