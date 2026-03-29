package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioRequestDTO(
         @Size(min = 3, max = 10)
         @Schema(description = "Nome do usuário")
         String name,
         @Email
         @Schema(description = "Email do usuário")
         String email,
         @Size(min = 8)
         @NotBlank
         @Schema(description = "Senha do usuário")
         String password,
         @Schema(description = "Base 64 da imagem")
         String foto,
         @NotBlank
         @Schema(description = "CPF do usuário")
         String cpf,

         @Schema(description = "Data de nascimento no formato yyyy-mm-dd")
         LocalDate dataNasc,
         @NotBlank
         @Schema(description = "Cargo do usuário")
         String cargo
) {
}
