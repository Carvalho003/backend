package school.sptech.EncantoPersonalizados.infrastructure.dto.frete;

public record FreteResponseDTO(
        Integer id,
        String name,
        String companyName,
        Double price,
        Integer deliveryTime
) {}