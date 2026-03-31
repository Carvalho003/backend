package school.sptech.EncantoPersonalizados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;

import java.util.List;

public interface WhatsappRepository extends JpaRepository<PedidoStatusPedido, Integer> {

        @Query("""
                        SELECT DISTINCT c.telefone
                        FROM PedidoStatusPedido psp
                        JOIN psp.pedido p
                        JOIN p.cliente c
                        JOIN psp.status s
                        WHERE psp.statusAtual = true
                            AND p.ativo = true
                            AND c.telefone IS NOT NULL
                            AND c.telefone <> ''
                            AND LOWER(s.status) NOT IN ('entregue', 'cancelado')
                        """)
        List<String> listarTelefonesEntregasPendentes();
}
