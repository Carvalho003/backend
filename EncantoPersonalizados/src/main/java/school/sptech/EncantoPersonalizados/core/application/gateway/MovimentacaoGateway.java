package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;

import java.time.LocalDate;
import java.util.Optional;

public interface MovimentacaoGateway {
    Movimentacao save(Movimentacao movimentacao);
    Optional<Movimentacao> findById(Integer id);
    Optional<Movimentacao> findByIdAndStatusTrue(Integer id);
    Page<Movimentacao> filtrar(
            String search,
            String tipo,
            Double valor,
            String categoria,
            String contraparte,
            String nome,
            Boolean status,
            String statusPagamento,
            LocalDate dataVencInicio,
            LocalDate dataVencFim,
            LocalDate dataPagInicio,
            LocalDate dataPagFim,
            Pageable pageable
    );
    void deleteById(Integer id);
}
