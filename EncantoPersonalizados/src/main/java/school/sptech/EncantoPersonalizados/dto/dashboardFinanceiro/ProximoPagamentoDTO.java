package school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro;

import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardProximosPagamentos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public record ProximoPagamentoDTO(
        String descricao,
        String categoria,
        BigDecimal valor,
        String dataVencimentoFormatada,
        String statusTexto,
        Boolean isAtrasado
) {
    public ProximoPagamentoDTO(DashboardProximosPagamentos entity) {
        this(
                entity.getDescricao(),
                entity.getCategoria(),
                entity.getValor(),
                entity.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                calcularTextoStatus(entity.getDataVencimento()),
                entity.getDataVencimento().isBefore(LocalDate.now())
        );
    }

    private static String calcularTextoStatus(LocalDate vencimento) {
        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(vencimento, hoje);

        if (dias > 0) {
            return "Venceu há " + dias + " dias";
        } else if (dias == 0) {
            return "Vence hoje";
        } else {
            return "Vence em " + Math.abs(dias) + " dias";
        }
    }
}
