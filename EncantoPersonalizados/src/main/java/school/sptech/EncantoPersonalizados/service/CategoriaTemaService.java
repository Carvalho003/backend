package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaMapper;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.CategoriaTemaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaTemaService {
    private final CategoriaTemaRepository repository;


    public CategoriaTemaService(CategoriaTemaRepository repository) {
        this.repository = repository;
    }

    public CategoriaTemaResponseDTO criar(CategoriaTemaRequestDTO dto){
        CategoriaTema entity = repository.save(CategoriaTemaMapper.toEntity(dto));
        return CategoriaTemaMapper.toDto(entity);
    }

    public CategoriaTemaResponseDTO update(CategoriaTemaRequestDTO dto, Integer id){
        CategoriaTema entityAntiga = this.findById(id);

        if(entityAntiga == null) throw new CategoriaTemaNaoEncontradaException("Categoria de Tema não encontrada");

        entityAntiga.setTitulo(dto.titulo());


        CategoriaTema entity = repository.save(CategoriaTemaMapper.toEntity(dto));
        return CategoriaTemaMapper.toDto(entity);
    }

    public Page<CategoriaTema> listar(String search, Boolean ativo, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<CategoriaTema> entities = repository.filtrar(search, ativo, pageable);


        return entities;
    }

    public CategoriaTema findById(Integer id){
        Optional<CategoriaTema> optionalEntity = repository.findById(id);
        if(optionalEntity.isEmpty()) return null;
        return optionalEntity.get();
    }

    public void mudarEstado(Integer id)
    {
        CategoriaTema entity = this.findById(id);
        if(entity == null) throw new CategoriaTemaNaoEncontradaException("Categoria de tema não encontrado");
        if(entity.getAtivo()){
            entity.setAtivo(false);
        }else{
            entity.setAtivo(true);
        }
        repository.save(entity);
    }
}
