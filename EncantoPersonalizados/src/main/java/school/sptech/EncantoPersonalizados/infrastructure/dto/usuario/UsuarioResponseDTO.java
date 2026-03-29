package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponseDTO(

         Integer id,
         @Schema(description = "Nome do usuário")
         String name,
         @Schema(description = "Email do usuário")
         String email,
         @Schema(description = "Path da imagem")
         String foto,
         @Schema(description = "CPF do usuário")
         String cpf,
         @Schema(description = "Data de nascimento no formato yyyy-mm-dd")
         LocalDate dataNasc,
         @Schema(description = "Cargo do usuário")
         String cargo,
         @Schema(description = "Data da criação do usuário")
         LocalDateTime createdAt,
         @Schema(description = "Data da última atualização do usuário")
         LocalDateTime updatedAt
) {
}
