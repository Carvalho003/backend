package school.sptech.EncantoPersonalizados.dto.cliente;

import java.time.LocalDateTime;

public record EnderecoClienteRequestDTO(
         String logradouro,
         String numLogradouro,
         String bairro,
         String cep,
         String uf,
         String cidade,
         String estado,
         String municipio,
         String complemento

) {
}
