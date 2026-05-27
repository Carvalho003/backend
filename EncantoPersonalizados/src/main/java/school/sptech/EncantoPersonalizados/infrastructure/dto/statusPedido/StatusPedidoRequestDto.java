package school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido;

import school.sptech.EncantoPersonalizados.core.domain.StatusPedidoRole;

public record StatusPedidoRequestDto(
        String status,
        String cor,
        Integer ordemKanban,
        StatusPedidoRole role
) {
    public StatusPedidoRequestDto(String status, String cor, Integer ordemKanban) {
        this(status, cor, ordemKanban, null);
    }
}
