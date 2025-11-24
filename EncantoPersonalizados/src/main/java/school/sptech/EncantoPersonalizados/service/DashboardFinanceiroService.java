package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.KpiDTO;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardKpi;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardDespesasRepository;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardKpiRepository;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardVendasRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardFinanceiroService {
    private final DashboardKpiRepository kpiRepo;
    private final DashboardVendasRepository vendasRepo;
    private final DashboardDespesasRepository despesasRepo;

    public DashboardFinanceiroService(DashboardKpiRepository kpiRepo, DashboardVendasRepository vendasRepo, DashboardDespesasRepository despesasRepo) {
        this.kpiRepo = kpiRepo;
        this.vendasRepo = vendasRepo;
        this.despesasRepo = despesasRepo;
    }

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

        response.put("kpi", new KpiDTO(totalReceita, totalDespesa, totalLucro));

        return response;
    }
}
