package school.sptech.EncantoPersonalizados.core.application.usecase.temaProduto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaTemaGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.TemaProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.core.domain.exception.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.domain.exception.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoRequestDTO;

import java.time.LocalDateTime;

@Component
public class TemaProdutoUseCaseImpl implements TemaProdutoUseCase {

    private final TemaProdutoGateway temaProdutoGateway;
    private final CategoriaTemaGateway categoriaTemaGateway;

    public TemaProdutoUseCaseImpl(TemaProdutoGateway temaProdutoGateway, CategoriaTemaGateway categoriaTemaGateway) {
        this.temaProdutoGateway = temaProdutoGateway;
        this.categoriaTemaGateway = categoriaTemaGateway;
    }

    @Override
    public TemaProduto findById(Integer id) {
        return temaProdutoGateway.findById(id).orElse(null);
    }

    @Override
    public Page<TemaProduto> get(String search, String categoria, int page, boolean ativo) {
        Pageable pageable = PageRequest.of(page, 10);
        return temaProdutoGateway.filtrar(search, categoria, ativo, pageable);
    }

    @Override
    public TemaProduto store(TemaProdutoRequestDTO dto) {
        CategoriaTema categoriaTema = categoriaTemaGateway.findById(dto.categoriaTemaId()).orElse(null);
        if (categoriaTema == null) throw new CategoriaTemaNaoEncontradaException("Categoria do tema não encontrada");

        TemaProduto entity = TemaProdutoMapper.toEntity(dto);
        entity.setCategoriaTema(categoriaTema);
        return temaProdutoGateway.save(entity);
    }

    @Override
    public TemaProduto update(TemaProdutoRequestDTO dto, Integer id) {
        TemaProduto entityAntiga = this.findById(id);
        if (entityAntiga == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        CategoriaTema categoriaTema = categoriaTemaGateway.findById(dto.categoriaTemaId()).orElse(null);
        if (categoriaTema == null) throw new CategoriaTemaNaoEncontradaException("Categoria do tema não encontrada");

        TemaProduto entity = TemaProdutoMapper.toEntity(dto);
        entityAntiga.setCategoriaTema(categoriaTema);
        entityAntiga.setUpdatedAt(LocalDateTime.now());
        entityAntiga.setDescricao(dto.descricao());
        return temaProdutoGateway.save(entity);
    }

    @Override
    public void mudarEstado(Integer id) {
        TemaProduto entity = this.findById(id);
        if (entity == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");
        entity.setAtivo(!entity.getAtivo());
        temaProdutoGateway.save(entity);
    }
}
