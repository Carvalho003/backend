package school.sptech.EncantoPersonalizados.infrastructure.dto.frete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CalcularFreteRequestDTO(
        @NotBlank String cepOrigem,
        @NotBlank String cepDestino,
        @NotEmpty List<FreteProdutoDTO> produtos
) {}