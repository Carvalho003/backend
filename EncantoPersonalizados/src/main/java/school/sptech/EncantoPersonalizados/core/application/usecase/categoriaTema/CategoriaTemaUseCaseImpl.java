package school.sptech.EncantoPersonalizados.core.application.usecase.categoriaTema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaTemaGateway;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.core.domain.exception.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaResponseDTO;

@Component
public class CategoriaTemaUseCaseImpl implements CategoriaTemaUseCase {

    private final CategoriaTemaGateway gateway;

    public CategoriaTemaUseCaseImpl(CategoriaTemaGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CategoriaTemaResponseDTO criar(CategoriaTemaRequestDTO dto) {
        CategoriaTema entity = gateway.save(CategoriaTemaMapper.toEntity(dto));
        return CategoriaTemaMapper.toDto(entity);
    }

    @Override
    public CategoriaTemaResponseDTO update(CategoriaTemaRequestDTO dto, Integer id) {
        CategoriaTema entityAntiga = this.findById(id);
        if (entityAntiga == null) throw new CategoriaTemaNaoEncontradaException("Categoria de Tema não encontrada");

        entityAntiga.setTitulo(dto.titulo());

        CategoriaTema entity = gateway.save(CategoriaTemaMapper.toEntity(dto));
        return CategoriaTemaMapper.toDto(entity);
    }

    @Override
    public Page<CategoriaTema> listar(String search, Boolean ativo, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return gateway.filtrar(search, ativo, pageable);
    }

    @Override
    public CategoriaTema findById(Integer id) {
        return gateway.findById(id).orElse(null);
    }

    @Override
    public void mudarEstado(Integer id) {
        CategoriaTema entity = this.findById(id);
        if (entity == null) throw new CategoriaTemaNaoEncontradaException("Categoria de tema não encontrado");
        entity.setAtivo(!entity.getAtivo());
        gateway.save(entity);
    }
}
