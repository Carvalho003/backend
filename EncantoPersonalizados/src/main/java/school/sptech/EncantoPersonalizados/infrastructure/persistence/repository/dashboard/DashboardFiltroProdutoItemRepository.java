package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.core.domain.dashboard.DashboardFiltroProdutoItem;

import java.util.List;

public interface DashboardFiltroProdutoItemRepository extends JpaRepository<DashboardFiltroProdutoItem, Long> {

    @Query("SELECT d FROM DashboardFiltroProdutoItem d ORDER BY d.qtdProd DESC")
    List<DashboardFiltroProdutoItem> findAllOrderByQtdProdDesc();
}
