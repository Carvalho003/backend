package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.dto.contraparte.MapperContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.movimentacao.MapperMovimentacao;
import school.sptech.EncantoPersonalizados.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;
import school.sptech.EncantoPersonalizados.repository.CategoriaMovimentacaoRepository;
import school.sptech.EncantoPersonalizados.repository.ContraparteRepository;
import school.sptech.EncantoPersonalizados.repository.MovimentacaoRepository;

import java.util.Optional;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository repository;
    private final ContraparteRepository contraparteRepository;
    private final CategoriaMovimentacaoRepository categoriaMovimentacaoRepository;

    public MovimentacaoService(MovimentacaoRepository repository,
                               ContraparteRepository contraparteRepository,
                               CategoriaMovimentacaoRepository categoriaMovimentacaoRepository) {
        this.repository = repository;
        this.contraparteRepository = contraparteRepository;
        this.categoriaMovimentacaoRepository = categoriaMovimentacaoRepository;
    }

    public ResponseMovimentacaoDTO create(RequestMovimentacaoDTO dto) {

        var contraparteEntity = contraparteRepository
                .findByIdAndStatusTrue(dto.idContraparte())
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada ou inativa"));

        var categoriaEntity = categoriaMovimentacaoRepository
                .findByIdAndStatusTrue(dto.idCategoriaMovimentacao())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada ou inativa"));

        Movimentacao mov = MapperMovimentacao.toEntity(dto, contraparteEntity, categoriaEntity);

        Movimentacao saved = repository.save(mov);

        return MapperMovimentacao.toDto(saved);
    }

    public Page<ResponseMovimentacaoDTO> get(
            String search,
            String tipo,
            Double valor,
            String categoria,
            String contraparte,
            String nome,
            Boolean status,
            int page
    ) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        search = vazioParaNull(search);
        tipo = vazioParaNull(tipo);
        valor = doubleParaNull(valor);
        categoria = vazioParaNull(categoria);
        contraparte = vazioParaNull(contraparte);
        nome = vazioParaNull(nome);

        Page<Movimentacao> pagina = repository.filtrar(
                search, tipo, valor, categoria, contraparte, nome, status, pageable
        );

        return pagina.map(MapperMovimentacao::toDto);
    }

    public ResponseMovimentacaoDTO getById(Integer id) {
        Movimentacao mov = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        return MapperMovimentacao.toDto(mov);
    }

    public ResponseMovimentacaoDTO update(Integer id, RequestMovimentacaoDTO dto) {

        Movimentacao mov = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        mov.setTipo(dto.tipo());
        mov.setDescricao(dto.descricao());
        mov.setValor(dto.valor());

        var contraparteEntity = contraparteRepository
                .findByIdAndStatusTrue(dto.idContraparte())
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        var categoriaEntity = categoriaMovimentacaoRepository
                .findByIdAndStatusTrue(dto.idCategoriaMovimentacao())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        mov.setContraparte(contraparteEntity);
        mov.setCategoriaMovimentacao(categoriaEntity);

        repository.save(mov);

        return MapperMovimentacao.toDto(mov);
    }

    public boolean delete(Integer id){
        Movimentacao mov = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        mov.setStatus(false);
        repository.save(mov);

        return true;
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private Double doubleParaNull(Double valor) {
        return (valor == null || valor.isNaN()) ? null : valor;
    }
}
