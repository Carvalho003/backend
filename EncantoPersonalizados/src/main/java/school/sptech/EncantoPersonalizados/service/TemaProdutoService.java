package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.TemaProdutoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TemaProdutoService {
    public final TemaProdutoRepository repository;
    private final CategoriaTemaService categoriaTemaService;


    public TemaProdutoService(TemaProdutoRepository repository, CategoriaTemaService categoriaTemaService) {
        this.repository = repository;
        this.categoriaTemaService = categoriaTemaService;
    }


    public TemaProduto findById(Integer id){
        Optional<TemaProduto> optionalTemaProduto = repository.findById(id);
        if(optionalTemaProduto.isEmpty()) return null;
        return optionalTemaProduto.get();
    }


    public Page<TemaProduto> get(String search, String categoria, int page, boolean ativo){
        Pageable pageable = PageRequest.of(page, 10);


        Page<TemaProduto> entity = repository.filtrar(search, categoria, ativo, pageable);
        return entity;
    }

    public TemaProduto store(TemaProdutoRequestDTO dto){
        // verificar se existe a categoria
        CategoriaTema categoriaTema = categoriaTemaService.findById(dto.categoriaTemaId());
        if(categoriaTema == null) throw new CategoriaTemaNaoEncontradaException("Categoria do tema não encontrada");

        TemaProduto entity = TemaProdutoMapper.toEntity(dto);
        entity.setCategoriaTema(categoriaTema);

        TemaProduto entitySalva = repository.save(entity);
        return entitySalva;
    }

    public TemaProduto update(TemaProdutoRequestDTO dto, Integer id){
        // verificar se existe a categoria
        TemaProduto entityAntiga = this.findById(id);

        if(entityAntiga == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        CategoriaTema categoriaTema = categoriaTemaService.findById(dto.categoriaTemaId());
        if(categoriaTema == null) throw new CategoriaTemaNaoEncontradaException("Categoria do tema não encontrada");

        TemaProduto entity = TemaProdutoMapper.toEntity(dto);
        entityAntiga.setCategoriaTema(categoriaTema);
        entityAntiga.setUpdatedAt(LocalDateTime.now());
        entityAntiga.setDescricao(dto.descricao());

        TemaProduto entitySalva = repository.save(entity);
        return entitySalva;
    }

    public void mudarEstado(Integer id)
    {
        TemaProduto entity = this.findById(id);
        if(entity == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");
        if(entity.getAtivo()){
            entity.setAtivo(false);
        }else{
            entity.setAtivo(true);
        }
        repository.save(entity);
    }

}
