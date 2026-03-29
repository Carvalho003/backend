package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.MovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.MovimentacaoRepository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class MovimentacaoRepositoryAdapter implements MovimentacaoGateway {

    private final MovimentacaoRepository repository;

    public MovimentacaoRepositoryAdapter(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movimentacao save(Movimentacao movimentacao) {
        return repository.save(movimentacao);
    }

    @Override
    public Optional<Movimentacao> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Movimentacao> findByIdAndStatusTrue(Integer id) {
        return repository.findByIdAndStatusTrue(id);
    }

    @Override
    public Page<Movimentacao> filtrar(
            String search, String tipo, Double valor, String categoria,
            String contraparte, String nome, Boolean status, String statusPagamento,
            LocalDate dataVencInicio, LocalDate dataVencFim,
            LocalDate dataPagInicio, LocalDate dataPagFim,
            Pageable pageable) {
        return repository.filtrar(search, tipo, valor, categoria, contraparte, nome,
                status, statusPagamento, dataVencInicio, dataVencFim, dataPagInicio, dataPagFim, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
