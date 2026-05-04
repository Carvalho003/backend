package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.DashboardGestaoPedidosGateway;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.*;
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
    private final DashboardPedidosMesRepository pedidosMesRepo;
    private final DashboardCargaTrabalhoRepository cargaTrabalhoRepo;
    private final DashboardPedidoSemAtualizacaoRepository pedidoSemAtualizacaoRepo;

    public DashboardGestaoPedidosRepositoryAdapter(
            DashboardLeadtimeFuncionarioRepository leadtimeFuncionarioRepo,
            DashboardRetrabalhoQuantidadeMesRepository retrabalhoQuantidadeMesRepo,
            DashboardLeadtimeEtapaRepository leadtimeEtapaRepo,
            DashboardLeadtimeMensalRepository leadtimeMensalRepo,
            DashboardFiltroProdutoItemRepository filtroProdutoItemRepo,
            DashboardTipoPedidoRepository tipoPedidoRepo,
            DashboardPedidosMesRepository pedidosMesRepo,
            DashboardCargaTrabalhoRepository cargaTrabalhoRepo,
            DashboardPedidoSemAtualizacaoRepository pedidoSemAtualizacaoRepo) {
        this.leadtimeFuncionarioRepo = leadtimeFuncionarioRepo;
        this.retrabalhoQuantidadeMesRepo = retrabalhoQuantidadeMesRepo;
        this.leadtimeEtapaRepo = leadtimeEtapaRepo;
        this.leadtimeMensalRepo = leadtimeMensalRepo;
        this.filtroProdutoItemRepo = filtroProdutoItemRepo;
        this.tipoPedidoRepo = tipoPedidoRepo;
        this.pedidosMesRepo = pedidosMesRepo;
        this.cargaTrabalhoRepo = cargaTrabalhoRepo;
        this.pedidoSemAtualizacaoRepo = pedidoSemAtualizacaoRepo;
    }

    @Override
    public Map<String, Object> getDashboardData(LocalDate inicio, LocalDate fim, String tipoPedido, Long produtoId, Long temaId) {
        Map<String, Object> response = new HashMap<>();

        List<DashboardLeadtimeFuncionario> ltPorFuncionario = leadtimeFuncionarioRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("leadtimePorFuncionario", ltPorFuncionario);

        List<DashboardRetrabalhoQuantidadeMes> retrabalhoPorMes = retrabalhoQuantidadeMesRepo.findAllFiltered(inicio, fim, produtoId, temaId);
        response.put("retrabalhoQuantidadePorMes", retrabalhoPorMes);

        List<DashboardLeadtimeEtapa> ltPorEtapa = leadtimeEtapaRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("leadtimePorEtapa", ltPorEtapa);

        List<DashboardLeadtimeMensal> ltMensal = leadtimeMensalRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("leadtimeMensal", ltMensal);

        List<DashboardFiltroProdutoItem> produtosMaisPedidos = filtroProdutoItemRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("produtosMaisPedidos", produtosMaisPedidos);

        List<DashboardTipoPedido> tipos = tipoPedidoRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("tiposPedido", tipos);

        List<DashboardPedidosMes> pedidosPorMes = pedidosMesRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("pedidosPorMes", pedidosPorMes);

        List<DashboardCargaTrabalho> cargaTrabalho = cargaTrabalhoRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("cargaTrabalho", cargaTrabalho);

        List<DashboardPedidoSemAtualizacao> semAtualizacao = pedidoSemAtualizacaoRepo.findAllFiltered(inicio, fim, tipoPedido, produtoId, temaId);
        response.put("pedidosSemAtualizacao", semAtualizacao);

        return response;
    }
}
