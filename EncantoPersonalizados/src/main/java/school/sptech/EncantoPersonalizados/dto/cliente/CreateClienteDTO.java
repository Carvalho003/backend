package school.sptech.EncantoPersonalizados.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateClienteDTO(
        @NotNull
        @NotBlank
        @Schema(description = "Nome do cliente", example = "Zaqueu Fernandes")
        String nome,
        @NotNull
        @NotBlank
        @Schema(description = "Telefone do usuário, sem traços ou caracteres especiais", example = "5511930301020")
        String telefone,
        List<EnderecoClienteRequestDTO> enderecos
) {
}
