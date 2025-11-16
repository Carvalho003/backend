package school.sptech.EncantoPersonalizados.dto.cliente;

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
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
