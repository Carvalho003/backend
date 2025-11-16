package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.RequestCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.ListUserDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.repository.CategoriaMovimentacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaMovimentacaoService {
    private final CategoriaMovimentacaoRepository repository;

    public CategoriaMovimentacaoService(CategoriaMovimentacaoRepository repository) {
        this.repository = repository;
    }

    public ResponseCategoriaMovimentacaoDTO create(RequestCategoriaMovimentacaoDTO dto) {
        if (repository.existsByDescricao(dto.descricao())) {
            throw new RuntimeException("Já existe uma categoria com essa descrição");
        }
        CategoriaMovimentacao entity = MapperCategoriaMovimentacao.toEntity(dto);
        entity.setStatus(true);
        CategoriaMovimentacao saved = repository.save(entity);
        return MapperCategoriaMovimentacao.toDTO(saved);
    }

    public Page<CategoriaMovimentacao> get(
            String search,
            Boolean status,
            int page
    ) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        search = vazioParaNull(search);

        return repository.filtrar(search, status, pageable);
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    public ResponseCategoriaMovimentacaoDTO findById(Integer id) {
        Optional<CategoriaMovimentacao> entity = repository.findByIdAndStatusTrue(id);

        if (entity.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada");
        }
        CategoriaMovimentacao categoria = entity.get();

        return MapperCategoriaMovimentacao.toDTO(categoria);
    }

    public ResponseCategoriaMovimentacaoDTO update(Integer id, RequestCategoriaMovimentacaoDTO dto) {
        Optional<CategoriaMovimentacao> entity = repository.findByIdAndStatusTrue(id);

        if (entity.isPresent()) {
            CategoriaMovimentacao categoriaNova = entity.get();
            categoriaNova.setDescricao(dto.descricao());

            CategoriaMovimentacao update = repository.save(categoriaNova);

            return MapperCategoriaMovimentacao.toDTO(update);
        }

        throw new RuntimeException("Categoria não encontrada");
    }

    public void delete(Integer id) {
        Optional<CategoriaMovimentacao> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada");
        }

        CategoriaMovimentacao categoria = entity.get();
        categoria.setStatus(false);

        CategoriaMovimentacao delete = repository.save(categoria);
    }
}
