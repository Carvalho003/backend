package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.core.application.usecase.frete.FreteUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.frete.FreteUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.CalcularFreteRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.FreteResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    private final FreteUseCase freteUseCase;

    public FreteController(FreteUseCase freteUseCase) {
        this.freteUseCase = freteUseCase;
    }

    @Operation(description = "Calcula o frete consultando a API do Melhor Envio de forma segura")
    @PostMapping("/calcular")
    public ResponseEntity<List<FreteResponseDTO>> calcular(@RequestBody @Valid CalcularFreteRequestDTO request) {
        List<FreteResponseDTO> opcoesFrete = freteUseCase.calcularFrete(request);

        if (opcoesFrete.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(opcoesFrete);
    }
}