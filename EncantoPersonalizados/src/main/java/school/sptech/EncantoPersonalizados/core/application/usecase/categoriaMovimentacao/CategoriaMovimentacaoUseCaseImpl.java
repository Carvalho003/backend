package school.sptech.EncantoPersonalizados.core.application.usecase.categoriaMovimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaMovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;

@Component
public class CategoriaMovimentacaoUseCaseImpl implements CategoriaMovimentacaoUseCase {

    private final CategoriaMovimentacaoGateway gateway;

    public CategoriaMovimentacaoUseCaseImpl(CategoriaMovimentacaoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public ResponseCategoriaMovimentacaoDTO create(RequestCategoriaMovimentacaoDTO dto) {
        if (gateway.existsByDescricao(dto.descricao())) {
            throw new RuntimeException("Já existe uma categoria com essa descrição");
        }
        CategoriaMovimentacao entity = MapperCategoriaMovimentacao.toEntity(dto);
        entity.setStatus(true);
        CategoriaMovimentacao saved = gateway.save(entity);
        return MapperCategoriaMovimentacao.toDTO(saved);
    }

    @Override
    public Page<CategoriaMovimentacao> get(String search, Boolean status, int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        search = vazioParaNull(search);
        return gateway.filtrar(search, status, pageable);
    }

    @Override
    public ResponseCategoriaMovimentacaoDTO findById(Integer id) {
        var entity = gateway.findByIdAndStatusTrue(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada");
        }
        CategoriaMovimentacao categoria = entity.get();
        return MapperCategoriaMovimentacao.toDTO(categoria);
    }

    @Override
    public ResponseCategoriaMovimentacaoDTO update(Integer id, RequestCategoriaMovimentacaoDTO dto) {
        var entity = gateway.findByIdAndStatusTrue(id);
        if (entity.isPresent()) {
            CategoriaMovimentacao categoriaNova = entity.get();
            categoriaNova.setDescricao(dto.descricao());
            CategoriaMovimentacao update = gateway.save(categoriaNova);
            return MapperCategoriaMovimentacao.toDTO(update);
        }
        throw new RuntimeException("Categoria não encontrada");
    }

    @Override
    public void delete(Integer id) {
        var entity = gateway.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada");
        }
        CategoriaMovimentacao categoria = entity.get();
        categoria.setStatus(false);
        gateway.save(categoria);
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
