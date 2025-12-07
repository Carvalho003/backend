package school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro;

import java.math.BigDecimal;

public record KpiDTO(
        BigDecimal totalReceita,
        BigDecimal totalDespesa,
        BigDecimal totalLucro,
        BigDecimal totalAPagar
) {

}