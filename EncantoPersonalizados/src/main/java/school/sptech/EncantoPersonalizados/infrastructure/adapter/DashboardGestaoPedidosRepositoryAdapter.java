package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardGestaoPedidosGateway;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardFiltroProdutoItem;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeEtapa;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeFuncionario;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardLeadtimeMensal;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardRetrabalhoQuantidadeMes;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardTipoPedido;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardGestaoPedidosRepositoryAdapter implements DashboardGestaoPedidosGateway {

    private final DashboardLeadtimeFuncionarioRepository leadtimeFuncionarioRepo;
    private final DashboardRetrabalhoQuantidadeMesRepository retrabalhoQuantidadeMesRepo;
    private final DashboardLeadtimeEtapaRepository leadtimeEtapaRepo;
    private final DashboardLeadtimeMensalRepository leadtimeMensalRepo;
    private final DashboardFiltroProdutoItemRepository filtroProdutoItemRepo;
    private final DashboardTipoPedidoRepository tipoPedidoRepo;

    public DashboardGestaoPedidosRepositoryAdapter(
            DashboardLeadtimeFuncionarioRepository leadtimeFuncionarioRepo,
            DashboardRetrabalhoQuantidadeMesRepository retrabalhoQuantidadeMesRepo,
            DashboardLeadtimeEtapaRepository leadtimeEtapaRepo,
            DashboardLeadtimeMensalRepository leadtimeMensalRepo,
            DashboardFiltroProdutoItemRepository filtroProdutoItemRepo,
            DashboardTipoPedidoRepository tipoPedidoRepo) {
        this.leadtimeFuncionarioRepo = leadtimeFuncionarioRepo;
        this.retrabalhoQuantidadeMesRepo = retrabalhoQuantidadeMesRepo;
        this.leadtimeEtapaRepo = leadtimeEtapaRepo;
        this.leadtimeMensalRepo = leadtimeMensalRepo;
        this.filtroProdutoItemRepo = filtroProdutoItemRepo;
        this.tipoPedidoRepo = tipoPedidoRepo;
    }

    @Override
    public Map<String, Object> getDashboardData(LocalDate inicio, LocalDate fim) {
        Map<String, Object> response = new HashMap<>();

        List<DashboardLeadtimeFuncionario> ltPorFuncionario = leadtimeFuncionarioRepo.findAllOrderByLeadTimeDesc();
        response.put("leadtimePorFuncionario", ltPorFuncionario);

        List<DashboardRetrabalhoQuantidadeMes> retrabalhoPorMes = retrabalhoQuantidadeMesRepo.findAllOrderByMesDesc();
        response.put("retrabalhoQuantidadePorMes", retrabalhoPorMes);

        List<DashboardLeadtimeEtapa> ltPorEtapa = leadtimeEtapaRepo.findAllOrderByLeadTimeDesc();
        response.put("leadtimePorEtapa", ltPorEtapa);

        List<DashboardLeadtimeMensal> ltMensal = leadtimeMensalRepo.findAllOrderByMesDesc();
        response.put("leadtimeMensal", ltMensal);

        List<DashboardFiltroProdutoItem> produtosMaisPedidos = filtroProdutoItemRepo.findAllOrderByQtdProdDesc();
        response.put("produtosMaisPedidos", produtosMaisPedidos);

        List<DashboardTipoPedido> tipos = tipoPedidoRepo.findAll();
        response.put("tiposPedido", tipos);

        return response;
    }
}
