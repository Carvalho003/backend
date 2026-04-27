package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.BuscarDashboardGestaoPedidosUseCase;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/dashgestaopedidos")
public class DashboardGestaoPedidosController {
    private final BuscarDashboardGestaoPedidosUseCase buscarDashboardGestaoPedidosUseCase;

    public DashboardGestaoPedidosController(BuscarDashboardGestaoPedidosUseCase buscarDashboardGestaoPedidosUseCase) {
        this.buscarDashboardGestaoPedidosUseCase = buscarDashboardGestaoPedidosUseCase;
    }

    @Operation(description = "Buscar dados para dashboard de gestão de pedidos")
    @ApiResponse(responseCode = "200", description = "Retorna dados para a dashboard")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardGestaoPedidos(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(value = "tipoPedido", required = false) String tipoPedido,
            @RequestParam(value = "produtoId", required = false) Long produtoId,
            @RequestParam(value = "temaId", required = false) Long temaId
    ) {
        return ResponseEntity.status(200).body(buscarDashboardGestaoPedidosUseCase.getDashboard(inicio, fim, tipoPedido, produtoId, temaId));
        }
}
