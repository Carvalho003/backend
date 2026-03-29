package school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema;

import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.CategoriaTema;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

import java.util.ArrayList;
import java.util.List;

public class CategoriaTemaMapper {

    public static CategoriaTema toEntity(CategoriaTemaRequestDTO dto){
        if (dto == null) return null;

        CategoriaTema entity = new CategoriaTema();
        entity.setTitulo(dto.titulo());
        return entity;
    }

    public static CategoriaTemaResponseDTO toDto(CategoriaTema entity){
        if(entity == null) return null;

        List<TemaProdutoResponseDTO> temas = new ArrayList<>();
        if(entity.getTemaProdutos() != null) {
            for (TemaProduto t : entity.getTemaProdutos()) {
                TemaProdutoResponseDTO tema = new TemaProdutoResponseDTO(
                        t.getId(),
                        t.getDescricao(),
                        t.getCreatedAt(),
                        t.getUpdatedAt()
                );
                temas.add(tema);
            }
        }
        return new CategoriaTemaResponseDTO(entity.getId(), entity.getTitulo(), temas);

    }

    public static List<CategoriaTemaResponseDTO> toDto(List<CategoriaTema> entities){
        if(entities == null) return null;

        return  entities
                .stream()
                .map(entity -> {


                    List<TemaProdutoResponseDTO> temas = new ArrayList<>();
                    if(entity.getTemaProdutos() != null) {
                        for (TemaProduto t : entity.getTemaProdutos()) {
                            TemaProdutoResponseDTO tema = new TemaProdutoResponseDTO(
                                    t.getId(),
                                    t.getDescricao(),
                                    t.getCreatedAt(),
                                    t.getUpdatedAt()
                            );
                            temas.add(tema);
                        }
                    }
                    return new CategoriaTemaResponseDTO(entity.getId(), entity.getTitulo(), temas);
                })
                .toList();


    }
}
