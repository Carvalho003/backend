package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardFinanceiroGateway;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardKpi;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardDespesasRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardKpiAPagarRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardKpiRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardProximosPagamentosRepository;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.DashboardVendasRepository;
import school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.KpiDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.dashboardFinanceiro.ProximoPagamentoDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardFinanceiroRepositoryAdapter implements DashboardFinanceiroGateway {

    private final DashboardKpiRepository kpiRepo;
    private final DashboardVendasRepository vendasRepo;
    private final DashboardDespesasRepository despesasRepo;
    private final DashboardKpiAPagarRepository kpiAPagarRepo;
    private final DashboardProximosPagamentosRepository proximosPagamentosRepo;

    public DashboardFinanceiroRepositoryAdapter(
            DashboardKpiRepository kpiRepo,
            DashboardVendasRepository vendasRepo,
            DashboardDespesasRepository despesasRepo,
            DashboardKpiAPagarRepository kpiAPagarRepo,
            DashboardProximosPagamentosRepository proximosPagamentosRepo) {
        this.kpiRepo = kpiRepo;
        this.vendasRepo = vendasRepo;
        this.despesasRepo = despesasRepo;
        this.kpiAPagarRepo = kpiAPagarRepo;
        this.proximosPagamentosRepo = proximosPagamentosRepo;
    }

    @Override
    public Map<String, Object> getDashboardData(LocalDate inicio, LocalDate fim) {
        Map<String, Object> response = new HashMap<>();

        List<DashboardKpi> evolucaoMensal = kpiRepo.findByPeriodo(inicio, fim);
        response.put("evolucaoMensal", evolucaoMensal);

        List<CategoriaDTO> vendasPorCategoria = vendasRepo.findTotaisPorPeriodo(inicio, fim);
        response.put("categoriasMaisVendidas", vendasPorCategoria);

        List<CategoriaDTO> despesasPorCategoria = despesasRepo.findTotaisPorPeriodo(inicio, fim);
        response.put("despesasPorCategoria", despesasPorCategoria);

        BigDecimal totalReceita = evolucaoMensal.stream().filter(
                k -> "RECEITA".equalsIgnoreCase(k.getTipoMovimentacao())
        ).map(DashboardKpi::getValorMesAtual).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesa = evolucaoMensal.stream().filter(
                k -> "DESPESA".equalsIgnoreCase(k.getTipoMovimentacao())
        ).map(DashboardKpi::getValorMesAtual).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLucro = totalReceita.subtract(totalDespesa);

        BigDecimal totalAPagar = kpiAPagarRepo.totalAPagarNoPeriodo(inicio, fim);

        response.put("kpi", new KpiDTO(totalReceita, totalDespesa, totalLucro, totalAPagar));

        List<ProximoPagamentoDTO> listaPagamentos = proximosPagamentosRepo.findProximosPagamentos(inicio, fim)
                .stream()
                .map(ProximoPagamentoDTO::new)
                .toList();

        response.put("proximosPagamentos", listaPagamentos);

        return response;
    }
}
