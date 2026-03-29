package school.sptech.EncantoPersonalizados.infrastructure.dto.statusPedido;

public record StatusPedidoRequestDto(
        String status,
        String cor,
        Integer ordemKanban
) {
}
