package school.sptech.EncantoPersonalizados.infrastructure.dto.frete;

public record FreteProdutoDTO(
        String id,
        Double width,
        Double height,
        Double length,
        Double weight,
        Double insurance_value,
        Integer quantity
) {}