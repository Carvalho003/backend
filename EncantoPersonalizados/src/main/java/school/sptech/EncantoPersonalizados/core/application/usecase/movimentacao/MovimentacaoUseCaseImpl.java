package school.sptech.EncantoPersonalizados.core.application.usecase.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaMovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ContraparteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.MovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Movimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.MapperMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;

import java.time.LocalDate;

@Component
public class MovimentacaoUseCaseImpl implements MovimentacaoUseCase {

    private final MovimentacaoGateway gateway;
    private final ContraparteGateway contraparteGateway;
    private final CategoriaMovimentacaoGateway categoriaMovimentacaoGateway;

    public MovimentacaoUseCaseImpl(
            MovimentacaoGateway gateway,
            ContraparteGateway contraparteGateway,
            CategoriaMovimentacaoGateway categoriaMovimentacaoGateway
    ) {
        this.gateway = gateway;
        this.contraparteGateway = contraparteGateway;
        this.categoriaMovimentacaoGateway = categoriaMovimentacaoGateway;
    }

    @Override
    public ResponseMovimentacaoDTO create(RequestMovimentacaoDTO dto) {
        var contraparteEntity = contraparteGateway
                .findByIdAndStatusTrue(dto.idContraparte())
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada ou inativa"));

        var categoriaEntity = categoriaMovimentacaoGateway
                .findByIdAndStatusTrue(dto.idCategoriaMovimentacao())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada ou inativa"));

        Movimentacao mov = MapperMovimentacao.toEntity(dto, contraparteEntity, categoriaEntity);
        Movimentacao saved = gateway.save(mov);
        return MapperMovimentacao.toDto(saved);
    }

    @Override
    public Page<ResponseMovimentacaoDTO> get(
            String search, String tipo, Double valor, String categoria, String contraparte,
            String nome, Boolean status, String statusPagamento,
            LocalDate dataVencInicio, LocalDate dataVencFim,
            LocalDate dataPagInicio, LocalDate dataPagFim, int page) {

        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        search = vazioParaNull(search);
        tipo = vazioParaNull(tipo);
        valor = doubleParaNull(valor);
        categoria = vazioParaNull(categoria);
        contraparte = vazioParaNull(contraparte);
        nome = vazioParaNull(nome);
        statusPagamento = vazioParaNull(statusPagamento);
        dataVencInicio = dateParaNull(dataVencInicio);
        dataVencFim = dateParaNull(dataVencFim);
        dataPagInicio = dateParaNull(dataPagInicio);
        dataPagFim = dateParaNull(dataPagFim);

        Page<Movimentacao> pagina = gateway.filtrar(
                search, tipo, valor, categoria, contraparte, nome, status, statusPagamento,
                dataVencInicio, dataVencFim, dataPagInicio, dataPagFim, pageable
        );

        return pagina.map(MapperMovimentacao::toDto);
    }

    @Override
    public ResponseMovimentacaoDTO getById(Integer id) {
        Movimentacao mov = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));
        return MapperMovimentacao.toDto(mov);
    }

    @Override
    public ResponseMovimentacaoDTO update(Integer id, RequestMovimentacaoDTO dto) {
        Movimentacao mov = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        mov.setTipo(dto.tipo());
        mov.setDescricao(dto.descricao());
        mov.setValor(dto.valor());

        var contraparteEntity = contraparteGateway
                .findByIdAndStatusTrue(dto.idContraparte())
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        var categoriaEntity = categoriaMovimentacaoGateway
                .findByIdAndStatusTrue(dto.idCategoriaMovimentacao())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        mov.setContraparte(contraparteEntity);
        mov.setCategoriaMovimentacao(categoriaEntity);

        gateway.save(mov);
        return MapperMovimentacao.toDto(mov);
    }

    @Override
    public boolean delete(Integer id) {
        Movimentacao mov = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));
        mov.setStatus(false);
        gateway.save(mov);
        return true;
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private Double doubleParaNull(Double valor) {
        return (valor == null || valor.isNaN()) ? null : valor;
    }

    private LocalDate dateParaNull(LocalDate date) {
        return (date == null) ? null : date;
    }
}
