package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.CategoriaDTO;
import school.sptech.EncantoPersonalizados.dto.dashboardFinanceiro.KpiDTO;
import school.sptech.EncantoPersonalizados.entities.dashboardFinanceiro.DashboardKpi;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardDespesasRepository;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardKpiRepository;
import school.sptech.EncantoPersonalizados.repository.dashboardFinanceiro.DashboardVendasRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class DashboardFinanceiroServiceTest {
//
//    @Mock
//    private DashboardKpiRepository kpiRepo;
//
//    @Mock
//    private DashboardVendasRepository vendasRepo;
//
//    @Mock
//    private DashboardDespesasRepository despesasRepo;
//
//    @InjectMocks
//    private DashboardFinanceiroService service;
//
//    @Test
//    void deveRetornarDashboardCompleto() {
//        LocalDate inicio = LocalDate.of(2024, 1, 1);
//        LocalDate fim = LocalDate.of(2024, 12, 31);
//
//        DashboardKpi kpiReceita = mock(DashboardKpi.class);
//        when(kpiReceita.getTipoMovimentacao()).thenReturn("RECEITA");
//        when(kpiReceita.getValorMesAtual()).thenReturn(new BigDecimal("1000"));
//
//        DashboardKpi kpiDespesa = mock(DashboardKpi.class);
//        when(kpiDespesa.getTipoMovimentacao()).thenReturn("DESPESA");
//        when(kpiDespesa.getValorMesAtual()).thenReturn(new BigDecimal("400"));
//
//        when(kpiRepo.findByPeriodo(inicio, fim))
//                .thenReturn(List.of(kpiReceita, kpiDespesa));
//
//        List<CategoriaDTO> vendas = List.of(
//                new CategoriaDTO("Categoria A", new BigDecimal("500"))
//        );
//        when(vendasRepo.findTotaisPorPeriodo(inicio, fim))
//                .thenReturn(vendas);
//
//        List<CategoriaDTO> despesas = List.of(
//                new CategoriaDTO("Materiais", new BigDecimal("300"))
//        );
//        when(despesasRepo.findTotaisPorPeriodo(inicio, fim))
//                .thenReturn(despesas);
//
//        Map<String, Object> result = service.getDashboardData(inicio, fim);
//
//        assertNotNull(result);
//        assertTrue(result.containsKey("evolucaoMensal"));
//        assertTrue(result.containsKey("categoriasMaisVendidas"));
//        assertTrue(result.containsKey("despesasPorCategoria"));
//        assertTrue(result.containsKey("kpi"));
//
//        KpiDTO kpi = (KpiDTO) result.get("kpi");
//        assertEquals(new BigDecimal("1000"), kpi.totalReceita());
//        assertEquals(new BigDecimal("400"), kpi.totalDespesa());
//        assertEquals(new BigDecimal("600"), kpi.totalLucro());
//
//        List<CategoriaDTO> vendasRetorno = (List<CategoriaDTO>) result.get("categoriasMaisVendidas");
//        assertEquals(1, vendasRetorno.size());
//        assertEquals("Categoria A", vendasRetorno.get(0).categoria());
//
//        List<CategoriaDTO> despesasRetorno = (List<CategoriaDTO>) result.get("despesasPorCategoria");
//        assertEquals(1, despesasRetorno.size());
//        assertEquals("Materiais", despesasRetorno.get(0).categoria());
//
//        verify(kpiRepo, times(1)).findByPeriodo(inicio, fim);
//        verify(vendasRepo, times(1)).findTotaisPorPeriodo(inicio, fim);
//        verify(despesasRepo, times(1)).findTotaisPorPeriodo(inicio, fim);
//    }
//
//    @Test
//    void deveRetornarZerosQuandoNaoHouverMovimentacao() {
//        LocalDate inicio = LocalDate.now().minusMonths(1);
//        LocalDate fim = LocalDate.now();
//
//        when(kpiRepo.findByPeriodo(inicio, fim)).thenReturn(List.of());
//        when(vendasRepo.findTotaisPorPeriodo(inicio, fim)).thenReturn(List.of());
//        when(despesasRepo.findTotaisPorPeriodo(inicio, fim)).thenReturn(List.of());
//
//        Map<String, Object> result = service.getDashboardData(inicio, fim);
//
//        KpiDTO kpi = (KpiDTO) result.get("kpi");
//
//        assertEquals(BigDecimal.ZERO, kpi.totalReceita());
//        assertEquals(BigDecimal.ZERO, kpi.totalDespesa());
//        assertEquals(BigDecimal.ZERO, kpi.totalLucro());
//    }
}
