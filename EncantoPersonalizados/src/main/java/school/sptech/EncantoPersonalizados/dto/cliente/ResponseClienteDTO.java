package school.sptech.EncantoPersonalizados.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseClienteDTO(
        @Schema(description = "ID do usuário", example = "1")
        Integer id,
        @Schema(description = "Nome do usuário", example = "Zaqueu Fernandes")
        String nome,
        @Schema(description = "Telefone do usuário", example = "5511930301020")
        String telefone,
        @Schema(description = "Lista de endereços do cliente", example = """
                [{
                        "logradouro": "Rua exemplo",
                        "numLogradouro": "100" ...
                }, ...]
                """)
        List<EnderecoClienteResponseDTO> enderecos,
        @Schema(description = "Data de criação do usuário", example = "yyyy-mm-dd HH:ii:ss")
        LocalDateTime createdAt,
        @Schema(description = "Data de atualização do usuário", example = "yyyy-mm-dd HH:ii:ss")
        LocalDateTime updatedAt
) {
}
