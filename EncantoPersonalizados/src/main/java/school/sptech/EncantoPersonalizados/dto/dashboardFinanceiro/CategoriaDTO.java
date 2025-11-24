package school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro;

import java.math.BigDecimal;

public record CategoriaDTO(
        String categoria,
        BigDecimal total
) {

}