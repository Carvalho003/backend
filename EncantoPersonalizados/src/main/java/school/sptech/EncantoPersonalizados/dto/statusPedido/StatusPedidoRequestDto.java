package school.sptech.EncantoPersonalizados.dto.statusPedido;

public record StatusPedidoRequestDto(
        String status,
        String cor,
        Integer ordemKanban
) {
}
