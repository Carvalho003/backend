package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.TemaProdutoRepository;

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


    public List<TemaProdutoResponseDTO> get(){
        List<TemaProduto> entity = repository.findAll();
        return TemaProdutoMapper.toDto(entity);
    }

    public TemaProdutoResponseDTO store(TemaProdutoRequestDTO dto){
        // verificar se existe a categoria
        CategoriaTema categoriaTema = categoriaTemaService.findById(dto.categoriaTemaId());
        if(categoriaTema == null) throw new CategoriaTemaNaoEncontradaException("Categoria do tema não encontrada");

        TemaProduto entity = TemaProdutoMapper.toEntity(dto);
        entity.setCategoriaTema(categoriaTema);

        TemaProduto entitySalva = repository.save(entity);
        return TemaProdutoMapper.toDto(entitySalva);
    }

}
