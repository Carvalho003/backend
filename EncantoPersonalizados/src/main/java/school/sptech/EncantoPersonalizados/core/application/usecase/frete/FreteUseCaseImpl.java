package school.sptech.EncantoPersonalizados.core.application.usecase.frete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.CalcularFreteRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.frete.FreteResponseDTO;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FreteUseCaseImpl implements FreteUseCase {

    private static final Logger log = LoggerFactory.getLogger(FreteUseCaseImpl.class);
    private final RestTemplate restTemplate;
    private final String token;
    private final String emailContato;
    private final String apiUrl;

    public FreteUseCaseImpl(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${melhorenvio.token}") String token,
            @Value("${melhorenvio.email}") String emailContato,
            @Value("${melhorenvio.api.url}") String apiUrl
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.token = token;
        this.emailContato = emailContato;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<FreteResponseDTO> calcularFrete(CalcularFreteRequestDTO requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(this.token);
        headers.set("User-Agent", this.emailContato);

        Map<String, Object> body = new HashMap<>();
        body.put("from", Map.of("postal_code", requestDto.cepOrigem()));
        body.put("to", Map.of("postal_code", requestDto.cepDestino()));
        body.put("products", requestDto.produtos());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    this.apiUrl,
                    HttpMethod.POST,
                    entity,
                    Map[].class
            );

            if (response.getBody() == null) {
                return Collections.emptyList();
            }

            return Arrays.stream(response.getBody())
                    .filter(map -> map.get("error") == null)
                    .map(map -> {
                        Map<String, Object> company = (Map<String, Object>) map.get("company");
                        return new FreteResponseDTO(
                                (Integer) map.get("id"),
                                (String) map.get("name"),
                                company != null ? (String) company.get("name") : "",
                                Double.parseDouble(map.get("price").toString()),
                                (Integer) map.get("delivery_time")
                        );
                    })
                    .sorted(Comparator.comparing(FreteResponseDTO::price))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Erro ao calcular frete no Melhor Envio: {}", e.getMessage());
            throw new RuntimeException("Falha ao calcular frete com as transportadoras.");
        }
    }
}