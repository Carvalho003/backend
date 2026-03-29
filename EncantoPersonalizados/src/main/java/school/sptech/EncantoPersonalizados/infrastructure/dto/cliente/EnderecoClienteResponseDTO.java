package school.sptech.EncantoPersonalizados.infrastructure.dto.cliente;

import java.time.LocalDateTime;

public record EnderecoClienteResponseDTO(
        Integer id,
        String logradouro,
        String numLogradouro,
        String bairro,
        String cep,
        String uf,
        String cidade,
        String estado,
        String municipio,
        String complemento,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
