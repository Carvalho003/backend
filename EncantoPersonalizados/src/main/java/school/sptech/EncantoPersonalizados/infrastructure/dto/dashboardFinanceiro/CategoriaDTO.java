package school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro;

import java.math.BigDecimal;

public record CategoriaDTO(
        String categoria,
        BigDecimal total
) {

}
