package school.sptech.EncantoPersonalizados.service;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaMapper;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaRequestDTO;
import school.sptech.EncantoPersonalizados.dto.categoriaTema.CategoriaTemaResponseDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaTema;
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

    public List<CategoriaTemaResponseDTO> listar(){
        List<CategoriaTema> entities = repository.findAll();
        return CategoriaTemaMapper.toDto(entities);
    }

    public CategoriaTema findById(Integer id){
        Optional<CategoriaTema> optionalEntity = repository.findById(id);
        if(optionalEntity.isEmpty()) return null;
        return optionalEntity.get();
    }
}
