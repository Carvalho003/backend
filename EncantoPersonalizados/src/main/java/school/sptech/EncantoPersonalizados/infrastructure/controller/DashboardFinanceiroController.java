package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.EncantoPersonalizados.core.application.usecase.dashboard.BuscarDashboardFinanceiroUseCase;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/dashfinanceiros")
public class DashboardFinanceiroController {
    private final BuscarDashboardFinanceiroUseCase buscarDashboardFinanceiroUseCase;

    public DashboardFinanceiroController(BuscarDashboardFinanceiroUseCase buscarDashboardFinanceiroUseCase) {
        this.buscarDashboardFinanceiroUseCase = buscarDashboardFinanceiroUseCase;
    }

    @Operation(description = "Buscar dados para dashboard financeiro")
    @ApiResponse(responseCode = "200", description = "Retorna dados para dash")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardFinanceiro(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.status(200).body(buscarDashboardFinanceiroUseCase.getDashboard(inicio, fim));
    }
}
